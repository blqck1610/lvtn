package com.lvtn.user.service.imp;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.authentication.AuthenticationClient;
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
import com.lvtn.utils.dto.request.user.UpdatePasswordRequest;
import com.lvtn.utils.dto.request.user.UpdateUserRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.lvtn.utils.util.ResponseUtil.getApiResponse;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final NotificationConfig notificationConfig;
    private final RabbitMQMessageProducer producer;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;
    private final AuthenticationClient authenticationClient;

    @Override
    public UserResponse getUserResponse() {
        return mapper.fromUser(getUser());
    }

    @Override
    @Transactional
    public UserResponse update(UpdateUserRequest request) {
        User user = getUser();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setLastName(request.getLastName());
        user = userRepository.saveAndFlush(user);
        System.out.println(user);
        return mapper.fromUser(user);
    }

    @Override
    @Transactional
    public void changePassword(UpdatePasswordRequest request) {
        User user = getUser();
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new BaseException(ErrorCode.WRONG_PASSWORD.getCode(), ErrorCode.WRONG_PASSWORD.getMessage());
        }
        if(request.getCurrentPassword().equals(request.getNewPassword())){
            throw new BaseException(ErrorCode.NEW_PASSWORD_EQUALS_CURRENT_PASSWORD.getCode(), ErrorCode.NEW_PASSWORD_EQUALS_CURRENT_PASSWORD.getMessage());
        }
        if(!request.getConfirmNewPassword().equals(request.getNewPassword())){
            throw new BaseException(ErrorCode.CONFIRM_PASSWORD_NOT_MATCH.getCode(), ErrorCode.CONFIRM_PASSWORD_NOT_MATCH.getMessage());
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        authenticationClient.revokeAllTokens(user.getId().toString());
    }

    @Override
    @Transactional
    public void delete() {
        User user = getUser();
        user.setDelete(true);
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(String password) {
    }

    public Boolean isUserExists(String username) {
        return userRepository.getByUsername(username).isPresent();
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userRepository.getByUsername(username)
                    .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMessage()));
        }
        throw new BaseException(ErrorCode.UNAUTHENTICATED.getCode(), ErrorCode.UNAUTHENTICATED.getMessage());
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

    @Override
    @Transactional
    public ApiResponse<UserResponse> register(RegisterRequest request) {
        if (isUserExists(request.getUsername())) {
            return getApiResponse(ErrorCode.USER_ALREADY_EXIST.getCode(), ErrorCode.USER_ALREADY_EXIST.getMessage(), null);
        }
        User user = User.builder()
                .username(request.getUsername())
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
