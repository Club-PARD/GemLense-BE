package com.example.longkathon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                // HTTPS 강제 적용
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure()
                )
                // 로그인 설정 (예시, 필요에 따라 수정)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    // ForwardedHeaderFilter 빈 등록
    @Bean
    public ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
}

