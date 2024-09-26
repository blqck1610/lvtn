package com.lvtn.user.service;


import com.lvtn.user.entity.Address;
import com.lvtn.user.entity.User;
import com.lvtn.utils.dto.user.AddressDto;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserV0;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    public UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .address(user.getAddress())
                .build();
    }
    public UserV0 fromUserToV0(User user) {
        return UserV0.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().toString())
                .password(user.getPassword())
                .build();
    }

    public AddressDto fromAddress(Address address) {
        return AddressDto.builder()
//                .userId(address.getUserId())
                .id(address.getId())
                .city(address.getCity())
                .country(address.getCountry())
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .phoneNumber(address.getPhoneNumber())
                .streetAddress(address.getStreetAddress())
                .build();
    }
    public Address fromAddressDto(AddressDto address) {
        return Address.builder()
//                .userId(address.getUserId())
                .city(address.getCity())
                .country(address.getCountry())
                .firstName(address.getFirstName())
                .lastName(address.getLastName())
                .phoneNumber(address.getPhoneNumber())
                .streetAddress(address.getStreetAddress())
                .build();
    }
}
