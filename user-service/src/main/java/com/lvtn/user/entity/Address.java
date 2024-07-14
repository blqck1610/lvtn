package com.lvtn.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @SequenceGenerator(
            name = "address_id_sequence",
            sequenceName = "address_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_id_sequence")
    private Integer id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String country;
    private String streetAddress;
    private String city;
    private String phoneNumber;
}
