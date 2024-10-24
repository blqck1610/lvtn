package com.lvtn.authentication.repository;

import com.lvtn.authentication.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t WHERE t.userId = :userId AND  t.isRevoked = false ")
    List<Token> findAllValidTokensByUser(@Param("userId") UUID userId);

    @Query("SELECT t FROM Token t WHERE t.accessToken = :token AND t.isRevoked != true ")
    Optional<Token> findByAccessToken(@Param("token") String token);

    @Query("SELECT t FROM Token t WHERE t.refreshToken = :token AND t.isRevoked != true ")
    Optional<Token> findByRefreshToken(@Param("token") String refreshToken);
}
