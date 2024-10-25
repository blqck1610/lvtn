package com.lvtn.authentication.service;

import com.lvtn.authentication.entity.TokenType;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RefreshTokenRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.authenticate.AuthResponse;

import java.util.Map;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse getToken(AuthRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    Boolean isTokenValid(String token, TokenType tokenType);

    void revokeAllTokens(String userId);

    ApiResponse<Map<String, Object>> getAllClaims(String token);
}
