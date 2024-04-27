package com.lvtn.user.service;

import com.lvtn.clients.user.AuthRequest;
import com.lvtn.clients.user.UserDto;
import com.lvtn.clients.user.UserRegistrationRequest;
import com.lvtn.exception.BaseException;

import com.lvtn.user.entity.User;
import com.lvtn.user.repository.UserRepository;
import com.lvtn.utils.ApiResponse;
import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;






    @Transactional(rollbackFor = { SQLException.class, Exception.class })
    public ApiResponse registerNewUser(UserRegistrationRequest request){
        if(userRepository.getByUsername(request.getUsername()) != null){
            throw new BaseException(400,"User already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .creatAt(LocalDateTime.now())
                .build();
        user = userRepository.saveAndFlush(user);
        //todo: publish notification


        return new ApiResponse(201, "create successfully");
    }
    @Transactional(rollbackFor = { SQLException.class, Exception.class })
    public ApiResponse deleteUser(Integer userId){
        User user = userRepository.getReferenceById(userId);
        userRepository.delete(user);

        return new ApiResponse(200,  "User deleted");
    }

    public ApiResponse changePassword(String password){
//        todo: change password
        return null;
    }
    public ApiResponse update(){
//        todo: update user
        return null;
    }

    public UserDto getUserInfo(String username) {

        User user = userRepository.getByUsername(username);
        if (user == null){
            return null;
        }
        return  UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
