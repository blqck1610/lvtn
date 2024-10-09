package com.lvtn.user.service.imp;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.product.ProductClient;
import com.lvtn.user.entity.User;
import com.lvtn.user.rabbitmq.config.NotificationConfig;
import com.lvtn.user.repository.UserRepository;
import com.lvtn.user.service.UserMapper;
import com.lvtn.user.service.UserService;
import com.lvtn.utils.ErrorCode;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.authenticate.AuthRequest;
import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.utils.dto.notification.NotificationType;
import com.lvtn.utils.dto.user.Role;
import com.lvtn.utils.dto.user.UserDto;
import com.lvtn.utils.dto.user.UserRegistrationRequest;
import com.lvtn.utils.dto.user.UserRequest;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final NotificationConfig notificationConfig;
    private final RabbitMQMessageProducer producer;
    private final ProductClient productClient;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;
    private final UserMapper userMapper;



    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public String deleteUser(Integer userId) {
//       todo: delete user
        return null;
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void changePassword(String password) {
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public UserDto update(Integer userId, UserRequest userRequest) {
        return null;
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

//    =================================================================================================
//    INTERNAL

    @Override
    public ApiResponse<UserDto> authenticate(AuthRequest request) {
        Optional<User> user = userRepository.getByUsername(request.getUsername());
        if (user.isEmpty()) {
            return ApiResponse.<UserDto>builder()
                    .code(ErrorCode.USER_NOT_FOUND.getCode())
                    .message(ErrorCode.USER_NOT_FOUND.getMessage())
                    .data(null)
                    .build();
        }
        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return ApiResponse.<UserDto>builder()
                    .code(ErrorCode.WRONG_PASSWORD.getCode())
                    .message(ErrorCode.WRONG_PASSWORD.getMessage())
                    .data(null)
                    .build();
        }
        return ApiResponse.<UserDto>builder()
                .code(HttpStatus.OK.value())
                .message("ok")
                .data(mapper.fromUser(user.get()))
                .build();

    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    @Override
    public ApiResponse<UserDto> registerNewUser(UserRegistrationRequest request) {
        if (isUserExists(request.getUsername())) {
            return ApiResponse.<UserDto>builder()
                    .code(ErrorCode.USER_ALREADY_EXIST.getCode())
                    .message(ErrorCode.USER_ALREADY_EXIST.getMessage())
                    .data(null)
                    .build();
        }
        User user = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .isDelete(false)
                .build();
        user = userRepository.saveAndFlush(user);
        //todo: publish notification
        publishNotification(user);

//        todo: create shopping cart
//        productClient.createCart(user.getId());
        return ApiResponse.<UserDto>builder()
                .code(200)
                .message("ok")
                .data(mapper.fromUser(user))
                .build();
    }

    private void publishNotification(User user) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .customerEmail(user.getEmail())
                .customerId(user.getId())
                .message("account create successfully at " + LocalDateTime.now() + ": email=" + user.getEmail())
                .type(NotificationType.NOTIFICATION)
                .build();
        producer.publish(notificationRequest,
                notificationConfig.getInternalExchange(),
                notificationConfig.getInternalNotificationRoutingKey());
    }

    @Override
    public ApiResponse<UserDto> getByUsername(String username) {
        Optional<User> user = userRepository.getByUsername(username);
        if (user.isEmpty()) {
            return ApiResponse.<UserDto>builder()
                    .code(ErrorCode.USER_NOT_FOUND.getCode())
                    .message(ErrorCode.USER_NOT_FOUND.getMessage())
                    .data(null)
                    .build();
        }
        return ApiResponse.<UserDto>builder()
                .code(200)
                .message("ok")
                .data(mapper.fromUser(user.get()))
                .build();

    }
//    public UserDto getUserInfo(Integer userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "user not found for id: " + userId));
//        return mapper.fromUser(user);
//    }
    //    public UserDto registerAdmin(UserRegistrationRequest request) {
//        if (isUserExists(request.getUsername())) {
//            throw new BaseException(HttpStatus.BAD_REQUEST, "User already exists");
//        }
//        User user = User.builder()
//                .username(request.getUsername())
//                .password(request.getPassword())
//                .email(request.getEmail())
//                .role(Role.ADMIN)
//                .provider(Provider.LOCAL)
//                .build();
//        user = userRepository.saveAndFlush(user);
//        return mapper.fromUser(user);
//
//
//    }
}
