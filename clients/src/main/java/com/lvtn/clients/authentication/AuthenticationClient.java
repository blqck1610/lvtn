package com.lvtn.clients.authentication;

import com.lvtn.utils.constant.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.lvtn.utils.constant.ApiEndpoint.*;
import static com.lvtn.utils.constant.ApiEndpoint.USER;
import static com.lvtn.utils.constant.ServiceName.AUTH_SERVICE;
import static com.lvtn.utils.constant.ServiceName.USER_SERVICE;

/**
 * AuthenticationClient
 * Version 1.0
 * Date: 26/09/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 26/09/2024        NGUYEN             create
 */
@FeignClient(value = AUTH_SERVICE, path = BASE_API + INTERNAL + AUTH)
public interface AuthenticationClient {

    @PostMapping(value = REVOKE_ALL_TOKEN)
    public void revokeAllTokens(@RequestBody String userId);
}
