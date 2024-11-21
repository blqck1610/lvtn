package com.lvtn.order.config.document;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * OpenApiConfig
 * Version 1.0
 * Date: 29/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 29/10/2024        NGUYEN             create
 */
@OpenAPIDefinition(
        info = @Info(
                title = "User Services API",
                description = "OpenApi Documentation for User services",
                version = "v1.0",
                contact = @Contact(
                        email = "tdnguyen16102002@gmail.com"
                ),
                license = @License(
                        name = "license name",
                        url = "url"
                ),
                termsOfService = "term"
        ),
        servers = {
                @Server(
                        description = "local ENV",
                        url = "http://localhost:8082"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {

}
