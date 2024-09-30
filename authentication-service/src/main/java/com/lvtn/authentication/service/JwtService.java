package com.lvtn.authentication.service;


import com.lvtn.utils.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long accessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public SecretKey getKey(){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
        } catch (JwtException e) {
            log.error(e.getMessage());
            throw new BaseException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    // extract to specific claim
    public <T> T extractClaims(String Token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(Token);
        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpired(String token){
        return extractClaims(token, Claims::getExpiration);
    }
    public Boolean isExpired(String token) {
        return extractExpired(token).before(new Date());

    }
    public String generateToken(String username, String role , String tokenType) {
        Map<String, String> claims = Map.of(
                "username", username , "role", role
        );
        if (tokenType.equals("ACCESS_TOKEN"))
            return buildToken(claims , accessTokenExpiration);
        else return buildToken(claims , refreshTokenExpiration);

    }
    private String buildToken(Map<String, String> claims, Long expiration ) {
        return Jwts.builder()
                .claims(claims)
                .subject(claims.get("username"))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }


//    public boolean isTokenValid(String jwtToken, String username) {
//        return (extractUsername(jwtToken).equals(username) && !isExpired(jwtToken));
//    }



}
