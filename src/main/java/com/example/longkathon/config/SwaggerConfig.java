package com.example.longkathon.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("1.0.0")
                .title("Wecand API")
                .description("Wecand API Documentation");
        Server server = new Server();
        server.setUrl("https://wecand.shop"); // HTTPS URL 설정

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }




    private Info apiInfo() {
        return new Info()
                .title("Wecand API")
                .description("Wecand API Documentation")
                .version("1.0.0");
    }
}