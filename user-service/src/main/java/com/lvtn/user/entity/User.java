package com.lvtn.user.entity;

import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User  {
    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Role role;

    private String address;
    private Provider provider;
//    todo: 2fa authenticate
    private LocalDateTime creatAt;

}
