package com.example.longkathon.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class  SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Wecand API")
                .version("1.0.0")
                .description("Wecand API Documentation");

        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Local server");

        Server prodServer = new Server()
                .url("https://wecand.shop")
                .description("Production server");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, prodServer));
    }
}
