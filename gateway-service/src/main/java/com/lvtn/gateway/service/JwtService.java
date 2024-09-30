package com.lvtn.gateway.service;

import com.lvtn.utils.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
        } catch (JwtException e) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    public Boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());

    }

    public String extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("role");

    }

}
