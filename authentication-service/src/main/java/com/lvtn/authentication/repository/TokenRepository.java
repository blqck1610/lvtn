package com.lvtn.authentication.repository;

import com.lvtn.authentication.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("SELECT t FROM Token t WHERE t.userId = :userId AND (t.isAccessExpired = false  or t.isRevoked = false )")
    List<Token> findAllValidTokensByUser(@Param("userId") Integer userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String refreshToken);
}
