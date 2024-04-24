package com.lvtn.user;

import com.lvtn.exception.BaseException;
import com.lvtn.utils.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = { SQLException.class, Exception.class })
    public ApiResponse registerNewUser(UserRegistrationRequest request){
        if(userRepository.getByUsername(request.getUsername()) != null){
            throw new BaseException(400,"User already exists");
        }

        return null;
    }




}
