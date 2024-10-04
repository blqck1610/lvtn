package com.lvtn.authentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.authentication.entity.Token;
import com.lvtn.authentication.entity.TokenType;
import com.lvtn.authentication.repository.TokenRepository;
import com.lvtn.clients.user.UserClient;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.authenticate.AuthResponse;
import com.lvtn.utils.dto.authenticate.TokenDto;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import com.lvtn.utils.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final UserClient userClient;
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    public  TokenDto findTokenByToken(String token, String tokenType) {
        Optional<Token> t;
        if(tokenType.equals("ACCESS_TOKEN")){
            t = tokenRepository.findByAccessToken(token);
        }
        else t = tokenRepository.findByRefreshToken(token);
        return t.map(tokenMapper::fromToken).orElse(null);
    }

    public ApiResponse<AuthResponse> registerNewUser(UserRegistrationRequest request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        ApiResponse<UserDto> response = userClient.register(request);
        check(response);
        UserDto user = response.getData();
        String accessToken = jwtService.generateToken(user.getUsername(), user.getRole().toString(), TokenType.BEARER.toString());
        String refreshToken = jwtService.generateToken(user.getUsername(), user.getRole().toString(), TokenType.BEARER.toString());
        return ApiResponse.<AuthResponse>builder()
                .code(HttpStatus.CREATED)
                .message(response.getMessage())
                .data(new AuthResponse(accessToken, refreshToken))
                .build();
    }



    public ApiResponse<AuthResponse> getToken(AuthRequest request) {
        ApiResponse<UserDto> response = userClient.authenticate(request);
        check(response);
        String accessToken = jwtService.generateToken(response.getData().getUsername(), response.getData().getRole().toString(), "ACCESS_TOKEN");
        String refreshToken = jwtService.generateToken(response.getData().getUsername(), response.getData().getRole().toString(), "REFRESH_TOKEN");
        revokeAllUserTokens(response.getData().getId());
        saveUserToken(response.getData().getId(), accessToken, refreshToken);
        return ApiResponse.<AuthResponse>builder()
                .code(response.getCode())
                .message(response.getMessage())
                .data(new AuthResponse(accessToken, refreshToken))
                .build();
    }

    private static void check(ApiResponse<UserDto> response) {
        if(response.getCode().equals(HttpStatus.BAD_REQUEST) || response.getData() == null){
            throw new BaseException(response.getCode(), response.getMessage());
        }
    }

    // todo: fix refresh token -- refresh token add on header
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        String username;
        if (authHeader == null) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {

            ApiResponse<UserDto> user = userClient.getByUsername(username);
            check(user);
//             check token match db
            if(!isTokenValid(refreshToken, "REFRESH_TOKEN")){
                throw new BaseException(HttpStatus.UNAUTHORIZED,"Invalid refresh token");
            }

            String accessToken = jwtService.generateToken(user.getData().getUsername(), user.getData().getRole().toString(), "ACCESS_TOKEN");
            refreshToken = jwtService.generateToken(user.getData().getUsername(), user.getData().getRole().toString(), "REFRESH_TOKEN");
            AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

            revokeAllUserTokens(user.getData().getId());
            saveUserToken(user.getData().getId(), accessToken, refreshToken);
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }

    private void revokeAllUserTokens(Integer userId) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(userId);
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> {
            token.setAccessExpired(true);
            token.setRefreshExpired(true);

            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);

    }

    private void saveUserToken(Integer userId, String accessToken, String refreshToken) {
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .type(TokenType.BEARER)
                .isAccessExpired(false)
                .isRefreshExpired(false)
                .isRevoked(false)
                .userId(userId)
                .build();

        tokenRepository.save(token);
    }

    public String test() {
        return userClient.test("test").getBody();
    }

    public Boolean isTokenValid(String token, String tokenType) {
        Optional<Token> t;
        if(tokenType.equals("ACCESS_TOKEN")){
            t = tokenRepository.findByAccessToken(token);

        }
        else t = tokenRepository.findByRefreshToken(token);
        return t.isPresent() && !t.get().isRevoked();
    }
}
