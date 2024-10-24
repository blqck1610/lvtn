package com.lvtn.clients.authentication;

import com.lvtn.utils.constant.ServiceName;
import org.springframework.cloud.openfeign.FeignClient;

import static com.lvtn.utils.constant.ApiEndpoint.AUTH;
import static com.lvtn.utils.constant.ApiEndpoint.BASE_API;

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
@FeignClient(value = ServiceName.AUTH_SERVICE, path = BASE_API + AUTH)
public interface AuthenticationClient {

}
