package com.lvtn.clients.user;


import com.lvtn.clients.config.AuthenticationRequestInterceptor;
import com.lvtn.utils.constant.ApiEndpoint;
import com.lvtn.utils.constant.Attribute;
import com.lvtn.utils.dto.ApiResponse;
import com.lvtn.utils.dto.request.authenticate.AuthRequest;
import com.lvtn.utils.dto.request.authenticate.RegisterRequest;
import com.lvtn.utils.dto.response.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.lvtn.utils.constant.ApiEndpoint.*;
import static com.lvtn.utils.constant.ServiceName.USER_SERVICE;

@FeignClient(value = USER_SERVICE, path = BASE_API + INTERNAL + USER)
public interface UserClient {

    @PostMapping
    public ApiResponse<UserResponse> register(@RequestBody RegisterRequest request);

    @PostMapping(value = AUTHENTICATE)
    public ApiResponse<UserResponse> authenticate(@RequestBody AuthRequest request);

    @GetMapping(value = GET_BY_USERNAME)
    ApiResponse<UserResponse> getByUsername(@PathVariable(Attribute.USERNAME) String username);
}
