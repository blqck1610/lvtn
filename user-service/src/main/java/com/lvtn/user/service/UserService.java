package com.lvtn.user.service;

import com.lvtn.exception.BaseException;
import com.lvtn.user.dto.AuthRequest;
import com.lvtn.user.dto.UserDto;
import com.lvtn.user.dto.UserRegistrationRequest;
import com.lvtn.user.repository.UserRepository;
import com.lvtn.user.entity.User;
import com.lvtn.utils.ApiResponse;
import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserDto authenticate(AuthRequest authRequest){
        User user = userRepository.getByUsername(authRequest.getUsername());
        if (user == null){
            throw new BaseException(400, "username doesn't exist");
        }
        if(user.getPassword() != authRequest.getPassword()){
            throw new BaseException(400, "wrong password");
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

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
        return  UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
