package com.lvtn.authentication.service.imp;

import com.lvtn.authentication.entity.Token;
import com.lvtn.authentication.entity.TokenType;
import com.lvtn.authentication.repository.TokenRepository;
import com.lvtn.authentication.service.AuthService;
import com.lvtn.clients.user.UserClient;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.authenticate.AuthResponse;
import com.lvtn.utils.dto.response.user.UserResponse;
import com.lvtn.utils.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public AuthResponse getToken(AuthRequest request) {
        ApiResponse<UserResponse> response = userClient.authenticate(request);
        check(response);
        return getAuthResponse(response);
    }

    private AuthResponse getAuthResponse(ApiResponse<UserResponse> response) {
        String accessToken = jwtService.generateToken(response.getData().getUsername(), response.getData().getRole().toString(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(response.getData().getUsername(), response.getData().getRole().toString(), TokenType.REFRESH_TOKEN);
        revokeAllTokens(response.getData().getId());
        saveToken(response.getData().getId(), accessToken, refreshToken);
        return new AuthResponse(accessToken, refreshToken);
    }

    private void check(ApiResponse<UserResponse> response) {
        if (ObjectUtils.isEmpty(response.getData())) {
            throw new BaseException(response.getCode(), response.getMessage());
        }
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(HttpServletRequest request)
            throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        String username;
        if (ObjectUtils.isEmpty(authHeader)) {
            throw new BaseException(ErrorCode.TOKEN_INVALID.getCode(), ErrorCode.TOKEN_INVALID.getMessage());
        }
        refreshToken = authHeader.substring(7);
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

    private void revokeAllTokens(UUID userId) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(userId);
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(validTokens);

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

    @Override
    public Boolean isTokenValid(String token, TokenType tokenType) {
        Optional<Token> t;
        if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
            t = tokenRepository.findByAccessToken(token);
        } else t = tokenRepository.findByRefreshToken(token);
        return t.isPresent() && !t.get().isRevoked();
    }
}
