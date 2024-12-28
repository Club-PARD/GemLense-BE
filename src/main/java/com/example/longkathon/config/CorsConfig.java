package com.example.longkathon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // React 앱 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 허용할 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true); // 인증 정보 허용 (필요한 경우)
    }
}


