package com.lvtn.user.service;

import com.lvtn.amqp.RabbitMQMessageProducer;
//import com.lvtn.clients.cart.CartClient;
import com.lvtn.clients.notification.NotificationRequest;
import com.lvtn.clients.notification.NotificationType;
import com.lvtn.clients.product.ProductClient;
import com.lvtn.clients.user.UserForAuth;
import com.lvtn.clients.user.UserRegistrationRequest;
import com.lvtn.user.dto.AddressDto;
import com.lvtn.user.dto.UserDto;
import com.lvtn.user.dto.UserRequest;
import com.lvtn.user.entity.Address;
import com.lvtn.user.entity.User;
import com.lvtn.user.rabbitmq.config.NotificationConfig;
import com.lvtn.user.repository.AddressRepository;
import com.lvtn.user.repository.UserRepository;
import com.lvtn.utils.ApiResponse;
import com.lvtn.utils.Provider;
import com.lvtn.utils.Role;
import com.lvtn.utils.exception.BaseException;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse registerNewUser(UserRegistrationRequest request) {
        if (isUserExists(request.getUsername())) {
            throw new BaseException(400, "User already exists");
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
                .message("account create successfully at " + LocalDateTime.now() +": email=" + user.getEmail() + " username: " + user.getUsername())
                .type(NotificationType.NOTIFICATION)
                .build();
//        todo: create shopping cart
//        productClient.createCart(user.getId());

        producer.publish(notificationRequest,
                notificationConfig.getInternalExchange(),
                notificationConfig.getInternalNotificationRoutingKey());


        return new ApiResponse(201, "create successfully");
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ApiResponse deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(404, "user not found"));
        userRepository.delete(user);

        return new ApiResponse(200, "User deleted");
    }
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ApiResponse changePassword(String password) {
//        todo: change password
        return null;
    }
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public Integer update(Integer userId, UserRequest userRequest) {

//        todo: update user
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(404 , "user not found"));
        mergerUser(user, userRequest);
        userRepository.save(user);
        return userId;
    }

    private void mergerUser(User user, UserRequest userRequest) {
    }


    public UserDto getUserInfo(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            return null;
        }
        return mapper.fromUser(user);
    }
    public UserDto findByUsername(String username) {
        User user = userRepository.getByUsername(username);
        if(user == null){
            return null;
        }
        return mapper.fromUser(user);
    }

    public List<UserDto> findAll(){
        return  userRepository.findAll().stream().map(mapper::fromUser).collect(Collectors.toList());

    }



    public String test(){
        return System.getProperty("user.dir");
    }

    public Boolean isUserExists(String username) {
        User user = userRepository.getByUsername(username);
        return user != null;
    }

    public UserForAuth getUserForAuth(String username) {
        User user = userRepository.getByUsername(username);
        if(user == null){
            return null;
        }


        return UserForAuth.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole().toString())
                .build();
    }

    public ApiResponse registerAdmin(UserRegistrationRequest request) {
        if (isUserExists(request.getUsername())) {
            throw new BaseException(400, "User already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(Role.ADMIN)
                .provider(Provider.LOCAL)
                .build();
        user = userRepository.saveAndFlush(user);
       return null;


    }

    public String saveAddress(String username, AddressDto addressDto) {
        User user = userRepository.getByUsername(username);
        Address address = userMapper.fromAddressDto(addressDto);
        address.setUserId(user.getId());
        addressRepository.save(address);
        return "save address successfully";

    }

    public List<AddressDto> getAddress(String username) {
        User user = userRepository.getByUsername(username);
        return addressRepository.findByUserId(user.getId()).stream().map(userMapper::fromAddress).collect(Collectors.toList());
    }
}
