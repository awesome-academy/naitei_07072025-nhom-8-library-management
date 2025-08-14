package com.group8.library_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI libraryManagementAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Nhom8-Library_Management")
                        .version("1.0.0")
                        .description("Demo quản lý thư viện"))
                .servers(List.of(
                        new Server().url("http://localhost:8080")
                ));
    }
}
