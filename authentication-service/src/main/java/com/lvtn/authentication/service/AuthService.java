package com.lvtn.authentication.service;

import com.lvtn.authentication.entity.TokenType;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.auth.AuthRequest;
import com.lvtn.utils.dto.auth.RefreshTokenRequest;
import com.lvtn.utils.dto.auth.RegisterRequest;
import com.lvtn.utils.dto.auth.AuthResponse;

import java.util.Map;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse getToken(AuthRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    Boolean isTokenValid(String token, TokenType tokenType);

    void revokeAllTokens(String userId);

    ApiResponse<Map<String, Object>> getAllClaims(String token);
}
