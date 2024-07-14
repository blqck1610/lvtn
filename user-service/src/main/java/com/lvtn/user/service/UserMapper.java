package com.lvtn.user.service;


import com.lvtn.user.dto.AddressDto;
import com.lvtn.user.dto.UserDto;
import com.lvtn.user.entity.Address;
import com.lvtn.user.entity.User;
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
