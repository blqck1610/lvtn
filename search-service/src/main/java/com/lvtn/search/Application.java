package com.lvtn.search;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application
 * Version 1.0
 * Date: 01/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 01/11/2024        NGUYEN             create
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.lvtn.clients"})
public class Application {
}
