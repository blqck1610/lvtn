package com.lvtn.authentication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_token")
public class Token {

    @Id
    @SequenceGenerator(name = "token_id_sequence",
            sequenceName = "token_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_sequence")
    private Integer id;
    private String accessToken;
    private String refreshToken;


    @Enumerated(EnumType.STRING)
    private TokenType type;

    private boolean accessExpired;
    private boolean refreshExpired;

    private boolean revoked;
    private int userId;
}
