package com.lvtn.user.service;


import com.lvtn.user.entity.User;
import com.lvtn.utils.dto.response.user.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    public UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
//                .address(user.getAddress())
                .build();
    }
//    public UserV0 fromUserToV0(User user) {
//        return UserV0.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .role(user.getRole().toString())
//                .password(user.getPassword())
//                .build();
//    }
//
//    public AddressDto fromAddress(Address address) {
//        return AddressDto.builder()
////                .userId(address.getUserId())
//                .id(address.getId())
//                .city(address.getCity())
//                .country(address.getCountry())
//                .firstName(address.getFirstName())
//                .lastName(address.getLastName())
//                .phoneNumber(address.getPhoneNumber())
//                .streetAddress(address.getStreetAddress())
//                .build();
//    }
//    public Address fromAddressDto(AddressDto address) {
//        return Address.builder()
////                .userId(address.getUserId())
//                .city(address.getCity())
//                .country(address.getCountry())
//                .firstName(address.getFirstName())
//                .lastName(address.getLastName())
//                .phoneNumber(address.getPhoneNumber())
//                .streetAddress(address.getStreetAddress())
//                .build();
//    }
}
