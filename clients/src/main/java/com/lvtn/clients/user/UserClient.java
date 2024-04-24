package com.lvtn.clients.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "USER", path = "/api/v1/user")
public interface UserClient {

}
