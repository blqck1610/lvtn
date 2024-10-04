package com.lvtn.user.service;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.product.ProductClient;
import com.lvtn.user.entity.User;
import com.lvtn.user.rabbitmq.config.NotificationConfig;
import com.lvtn.user.repository.AddressRepository;
import com.lvtn.user.repository.UserRepository;
import com.lvtn.utils.Provider;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.utils.dto.notification.NotificationType;
import com.lvtn.utils.dto.user.*;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NotificationConfig notificationConfig;
    private final RabbitMQMessageProducer producer;
    private final ProductClient productClient;

    private final UserMapper mapper;
    private final UserMapper userMapper;
    private final AddressRepository addressRepository;

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ApiResponse<UserDto> registerNewUser(UserRegistrationRequest request) {
        if (isUserExists(request.getUsername())) {
            return ApiResponse.<UserDto>builder()
                    .code(HttpStatus.BAD_REQUEST)
                    .message("username already exists")
                    .data(null)
                    .build();
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(Role.USER)
                .provider(Provider.LOCAL)
                .build();
        user = userRepository.saveAndFlush(user);
        //todo: publish notification

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .customerEmail(user.getEmail())
                .customerId(user.getId())
                .message("account create successfully at " + LocalDateTime.now() + ": email=" + user.getEmail() + " username: " + user.getUsername())
                .type(NotificationType.NOTIFICATION)
                .build();
//        todo: create shopping cart
//        productClient.createCart(user.getId());

        producer.publish(notificationRequest,
                notificationConfig.getInternalExchange(),
                notificationConfig.getInternalNotificationRoutingKey());


        return ApiResponse.<UserDto>builder()
                .code(HttpStatus.CREATED)
                .message("created")
                .data(mapper.fromUser(user))
                .build();
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public String deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "user not found"));
        userRepository.delete(user);
        return "User id: + " + userId + " deleted";
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void changePassword(String password) {

    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public UserDto update(Integer userId, UserRequest userRequest) {
//        todo: implement update user
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "user not found"));
        mergerUser(user, userRequest);
        user = userRepository.saveAndFlush(user);
        return mapper.fromUser(user);
    }

    private void mergerUser(User user, UserRequest userRequest) {
    }


    public UserDto getUserInfo(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "user not found for id: " + userId));
        return mapper.fromUser(user);
    }

    public ApiResponse<UserDto> getByUsername(String username) {
        Optional<User> user = userRepository.getByUsername(username);
        if (user.isEmpty()) {
            return ApiResponse.<UserDto>builder()
                    .code(HttpStatus.BAD_REQUEST)
                    .message("user not found for username: " + username)
                    .data(null)
                    .build();
        }
        return ApiResponse.<UserDto>builder()
                .code(HttpStatus.OK)
                .message("ok")
                .data(mapper.fromUser(user.get()))
                .build();
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(mapper::fromUser).collect(Collectors.toList());

    }


    public String test() {
        return System.getProperty("user.dir");
    }

    public Boolean isUserExists(String username) {
        return userRepository.getByUsername(username).isPresent();

    }



    public UserDto registerAdmin(UserRegistrationRequest request) {
        if (isUserExists(request.getUsername())) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "User already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(Role.ADMIN)
                .provider(Provider.LOCAL)
                .build();
        user = userRepository.saveAndFlush(user);
        return mapper.fromUser(user);


    }





    public ApiResponse<UserDto> authenticate(AuthRequest request) {
        Optional<User> user = userRepository.getByUsername(request.getUsername());
        if (user.isEmpty()) {
            return ApiResponse.<UserDto>builder()
                    .code(HttpStatus.BAD_REQUEST)
                    .message("Username is not exist")
                    .data(null)
                    .build();
        }
        if(!BCrypt.checkpw( request.getPassword(),user.get().getPassword())){
            return ApiResponse.<UserDto>builder()
                    .code(HttpStatus.BAD_REQUEST)
                    .message("Password is incorrect")
                    .data(null)
                    .build();
        }
        return  ApiResponse.<UserDto>builder()
                .code(HttpStatus.OK)
                .message("ok")
                .data(mapper.fromUser(user.get()))
                .build();

    }
}
