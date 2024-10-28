package com.lvtn.product.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvtn.clients.authentication.AuthenticationClient;
import com.lvtn.utils.constant.Attribute;
import com.lvtn.utils.constant.Common;
import com.lvtn.utils.dto.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JwtAuthenticateFilter
 * Version 1.0
 * Date: 24/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 24/10/2024        NGUYEN             create
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticateFilter extends OncePerRequestFilter {
    private final AuthenticationClient authClient;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(Attribute.AUTHORIZATION);
        if (ObjectUtils.isEmpty(token) || !token.startsWith(Common.PREFIX_TOKEN)) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7);
        ApiResponse<Map<String, Object>> res = authClient.getAllClaims(token);
        if (ObjectUtils.isEmpty(res.getData())) {
            sendError(response, res.getCode(), res.getMessage());
            return;
        }
        String username = res.getData().get(Attribute.USERNAME).toString();
        List<GrantedAuthority> authorities = extractAuthorities(res.getData());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );
//                collect details about current web (remote address, session ID, etc) from http request
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
        request.setAttribute(Attribute.USERNAME, username);
        request.setAttribute(Attribute.ROLE, authorities);
        filterChain.doFilter(request, response);
    }

    private List<GrantedAuthority> extractAuthorities(Map<String, Object> claims) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Object role = claims.get(Attribute.ROLE);
        if (!ObjectUtils.isEmpty(role)) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }

    private static void sendError(HttpServletResponse response, int code, String mess) throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(code)
                .message(mess)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(code);
        response.setContentType(Common.JSON_CONTENT_TYPE);
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        //        response.flushBuffer();
        response.getWriter().flush();
    }
}
