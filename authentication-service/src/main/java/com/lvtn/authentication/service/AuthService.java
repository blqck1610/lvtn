package com.lvtn.authentication.service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.utils.dto.authenticate.AuthResponse;
import com.lvtn.authentication.entity.Token;
import com.lvtn.authentication.entity.TokenType;
import com.lvtn.authentication.repository.TokenRepository;
import com.lvtn.clients.user.*;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import com.lvtn.utils.dto.user.UserV0;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final JwtService jwtService;
    private final UserClient userClient;

    private final TokenRepository tokenRepository;

    public AuthResponse register(UserRegistrationRequest request) {
//        request.setPassword(CryptoUtil.encrypt(request.getPassword()));
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        UserV0 userV0 = userClient.register(request);

        String accessToken = jwtService.generateToken(userV0.getUsername(), userV0.getRole(), TokenType.BEARER.toString());
        String refreshToken = jwtService.generateToken(userV0.getUsername(), userV0.getRole(), TokenType.BEARER.toString());
        return new AuthResponse(accessToken, refreshToken);
    }

    public UserDto registerAdmin(UserRegistrationRequest request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        return userClient.registerAdmin(request);
    }

    public AuthResponse authenticate(UserV0 user) {
        String accessToken = jwtService.generateToken(user.getUsername(), user.getRole(), "ACCESS_TOKEN");
        String refreshToken = jwtService.generateToken(user.getUsername(), user.getRole(), "REFRESH_TOKEN");
        revokeAllUserTokens(user.getId());
        saveUserToken(user.getId(), refreshToken);
        return new AuthResponse(accessToken, refreshToken);
    }
// todo: fix refresh token
    public void refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws StreamWriteException, DatabindException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        String username;
        if (authHeader == null) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {

            UserV0 userDto = userClient.getUserForAuth(username);
            String accessToken = jwtService.generateToken(userDto.getUsername(), userDto.getRole().toString(), "ACCESS_TOKEN");
            var authResponse = new AuthResponse(accessToken, refreshToken);
            revokeAllUserTokens(userDto.getId());
            saveUserToken(userDto.getId(), accessToken);
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }

    private void revokeAllUserTokens(Integer userId) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(userId);
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);

    }

    private void saveUserToken(Integer userId, String accessToken) {
        Token token = Token.builder()
                .token(accessToken)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .userId(userId)
                .build();

        tokenRepository.save(token);
    }
    public String test(){
        return  userClient.test("test").getBody();
    }

}
