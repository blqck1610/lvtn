package com.lvtn.product.config;

import com.lvtn.product.config.filter.JwtAuthenticateFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 * Version 1.0
 * Date: 28/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 28/10/2024        NGUYEN             create
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/v1/internal/**",
            "/api/v1/public/**"
    };
    private final JwtAuthenticationEntryPoint jwtEntryPoint;
    private final JwtAuthenticateFilter jwtAthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection since we are using JWT tokens
                .csrf(AbstractHttpConfigurer::disable)
                // CORS configuration
                .cors(Customizer.withDefaults())
                // Authorization for public endpoints
                .authorizeHttpRequests(request ->
                        request.requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                                .anyRequest().authenticated()  // All other requests require authentication
                )
                // Use stateless session management because we're using JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtEntryPoint))
        ;
        return http.build();
    }
}
