package com.lvtn.user.service.imp;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.product.ProductClient;
import com.lvtn.user.entity.User;
import com.lvtn.user.rabbitmq.config.NotificationConfig;
import com.lvtn.user.repository.UserRepository;
import com.lvtn.user.service.UserMapper;
import com.lvtn.user.service.UserService;
import com.lvtn.utils.common.ErrorCode;
import com.lvtn.utils.common.Role;
import com.lvtn.utils.common.SuccessMessage;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.utils.dto.notification.NotificationType;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.request.user.UpdateUserRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
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


    @Transactional
    public String deleteUser(Integer userId) {
//       todo: delete user
        return null;
    }

    @Transactional
    public void changePassword(String password) {
    }

    @Transactional
    public UserResponse update(Integer userId, UpdateUserRequest userRequest) {
        return null;
    }


    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(mapper::fromUser).collect(Collectors.toList());

    }

    public String test() {
        return System.getProperty("user.dir");
    }

    public Boolean isUserExists(String username) {
        return userRepository.getByUsername(username).isPresent();

    }

    //    INTERNAL
    @Override
    public ApiResponse<UserResponse> authenticate(AuthRequest request) {
        Optional<User> user = userRepository.getByUsername(request.getUsername());
        int code = HttpStatus.OK.value();
        String message = SuccessMessage.OK.getMessage();
        if (user.isPresent()) {
            if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
                return getApiResponse(ErrorCode.WRONG_PASSWORD.getCode(), ErrorCode.WRONG_PASSWORD.getMessage(), null);
            }
            return getApiResponse(HttpStatus.OK.value(), SuccessMessage.OK.getMessage(), mapper.fromUser(user.get()));
        } else {
            return getApiResponse(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage(), null);
        }

    }

    private static ApiResponse<UserResponse> getApiResponse(int code, String message, UserResponse data) {
        return ApiResponse.<UserResponse>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    @Override
    @Transactional
    public ApiResponse<UserResponse> register(RegisterRequest request) {
        if (isUserExists(request.getUsername())) {
            return getApiResponse(ErrorCode.USER_ALREADY_EXIST.getCode(), ErrorCode.USER_ALREADY_EXIST.getMessage(), null);
        }
        User user = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .isDelete(false)
                .build();
        user = userRepository.saveAndFlush(user);
        //todo: publish notification
//        publishNotification(user);

//        todo: create shopping cart
//        productClient.createCart(user.getId());
        return getApiResponse(HttpStatus.OK.value(), SuccessMessage.OK.getMessage(), mapper.fromUser(user));
    }

//    TODO: implement publish notification
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
    public ApiResponse<UserResponse> getByUsername(String username) {
        Optional<User> user = userRepository.getByUsername(username);
        if (user.isEmpty()) {
            return getApiResponse(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage(), null);
        }
        return getApiResponse(200, SuccessMessage.OK.getMessage(), mapper.fromUser(user.get()));
    }

}
