package com.example.longkathon;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(servers = {@Server(url = "https://wecand.shop", description = "wecand")})

@SpringBootApplication
public class LongkathonApplication {

	public static void main(String[] args) {
		SpringApplication.run(LongkathonApplication.class, args);
	}

}
