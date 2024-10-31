package com.lvtn.authentication.service.imp;

import com.lvtn.authentication.entity.Token;
import com.lvtn.authentication.entity.TokenType;
import com.lvtn.authentication.repository.TokenRepository;
import com.lvtn.authentication.service.AuthService;
import com.lvtn.clients.user.UserClient;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RefreshTokenRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.authenticate.AuthResponse;
import com.lvtn.utils.dto.response.user.UserResponse;
import com.lvtn.utils.exception.BaseException;
import com.lvtn.utils.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService {
    private final JwtService jwtService;
    private final UserClient userClient;
    private final TokenRepository tokenRepository;

    @Override
    public void register(RegisterRequest request) {
        ApiResponse<UserResponse> response = userClient.register(request);
        check(response);
    }

    @Override
    @Transactional
    public AuthResponse getToken(AuthRequest request) {
        ApiResponse<UserResponse> response = userClient.authenticate(request);
        check(response);
        return getAuthResponse(response);
    }

    @Override
    public Boolean isTokenValid(String token, TokenType tokenType) {
        Optional<Token> t;
        if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
            t = tokenRepository.findByAccessToken(token);
        } else t = tokenRepository.findByRefreshToken(token);
        return t.isPresent() && !t.get().isRevoked();
    }

    @Override
    @Transactional
    public void revokeAllTokens(String userId) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(UUID.fromString(userId));
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(validTokens);
    }

    @Override
    public ApiResponse<Map<String, Object>> getAllClaims(String token) {
        if(!isTokenValid(token, TokenType.ACCESS_TOKEN)){
            return getApiResponse(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMessage(), null);
        }
        try {
            Claims claims = jwtService.extractAllClaims(token);
            return getApiResponse(HttpStatus.OK.value(), SuccessMessage.OK.getMessage(), claims);
        }
        catch (BaseException e){
            return getApiResponse(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMessage(), null);
        }
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String username;
        String refreshToken = request.getToken();
        try {
            username = jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMessage());
        }
        ApiResponse<UserResponse> userResponse = userClient.getByUsername(username);
        check(userResponse);
        if (!isTokenValid(refreshToken, TokenType.REFRESH_TOKEN)) {
            throw new BaseException(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMessage());
        }
        return getAuthResponse(userResponse);
    }

    private void check(ApiResponse<UserResponse> response) {
        if (ObjectUtils.isEmpty(response.getData())) {
            throw new BaseException(response.getCode(), response.getMessage());
        }
    }

    private void saveToken(UUID userId, String accessToken, String refreshToken) {
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .type(TokenType.BEARER)
                .isRevoked(false)
                .userId(userId)
                .build();
        tokenRepository.save(token);
    }

    @Transactional
    protected AuthResponse getAuthResponse(ApiResponse<UserResponse> response) {
        String accessToken = jwtService.generateToken(response.getData().getUsername(), response.getData().getRole(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(response.getData().getUsername(), response.getData().getRole(), TokenType.REFRESH_TOKEN);
        revokeAllTokens(String.valueOf(response.getData().getId()));
        saveToken(response.getData().getId(), accessToken, refreshToken);
        return new AuthResponse(accessToken, refreshToken);
    }
}
