package com.lvtn.authentication.service;

import com.lvtn.authentication.entity.TokenType;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.authenticate.AuthResponse;
import com.lvtn.utils.dto.response.authenticate.TokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface AuthService {
    void register(RegisterRequest request);

    AuthResponse getToken(AuthRequest request);

    @Transactional
    AuthResponse refreshToken(HttpServletRequest request)
            throws IOException;

    Boolean isTokenValid(String token, TokenType tokenType);
}
