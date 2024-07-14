package com.lvtn.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
//    private Integer userId;
    private Integer id;
    private String firstName;
    private String lastName;
    private String country;
    private String streetAddress;
    private String city;
    private String phoneNumber;
}
