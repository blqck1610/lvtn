package com.lvtn.user.service;

import com.lvtn.amqp.RabbitMQMessageProducer;
import com.lvtn.clients.product.ProductClient;
import com.lvtn.user.entity.Address;
import com.lvtn.user.entity.User;
import com.lvtn.user.rabbitmq.config.NotificationConfig;
import com.lvtn.user.repository.AddressRepository;
import com.lvtn.user.repository.UserRepository;
import com.lvtn.utils.Provider;
import com.lvtn.utils.dto.notification.NotificationRequest;
import com.lvtn.utils.dto.notification.NotificationType;
import com.lvtn.utils.dto.user.*;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
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
    public UserV0 registerNewUser(UserRegistrationRequest request) {
        if (isUserExists(request.getUsername())) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "User already exists");
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


        return mapper.fromUserToV0(user);
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

    public UserDto findByUsername(String username) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "user not found for username: " + username);
        }
        return mapper.fromUser(user);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(mapper::fromUser).collect(Collectors.toList());

    }


    public String test() {
        return System.getProperty("user.dir");
    }

    public Boolean isUserExists(String username) {
        User user = userRepository.getByUsername(username);
        return user != null;
    }

    public UserV0 getUserForAuth(String username) {
        User user = userRepository.getByUsername(username);
        if (user == null) {
            return null;
        }
        return UserV0.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
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

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public AddressDto saveAddress(String username, AddressDto addressDto) {
        // todo : implement add address
        User user = userRepository.getByUsername(username);
        Address address = userMapper.fromAddressDto(addressDto);
        address.setUserId(user.getId());
        addressRepository.save(address);
        return addressDto;

    }

    public List<AddressDto> getAddress(String username) {
        User user = userRepository.getByUsername(username);
        return
                addressRepository.findByUserId(user.getId()).stream().map(userMapper::fromAddress)
                        .collect(Collectors.toList());
    }
}
