package com.lvtn.authentication.service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.authentication.entity.Token;
import com.lvtn.authentication.entity.TokenType;
import com.lvtn.authentication.repository.TokenRepository;
import com.lvtn.clients.user.UserClient;
import com.lvtn.utils.dto.authenticate.AuthResponse;
import com.lvtn.utils.dto.authenticate.TokenDto;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import com.lvtn.utils.dto.user.UserV0;
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
        saveUserToken(user.getId(), accessToken, refreshToken);
        return new AuthResponse(accessToken, refreshToken);
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

            UserV0 userDto = userClient.getUserForAuth(username);
//             check token match db
            tokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED, "refreshToken invalid"));

            String accessToken = jwtService.generateToken(userDto.getUsername(), userDto.getRole(), "ACCESS_TOKEN");
            refreshToken = jwtService.generateToken(userDto.getUsername(), userDto.getRole(), "REFRESH_TOKEN");
            AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);

            revokeAllUserTokens(userDto.getId());
            saveUserToken(userDto.getId(), accessToken, refreshToken);
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
