package com.example.longkathon.config;

import com.example.longkathon.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .sessionManagement(session -> session
                        .sessionFixation().newSession())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/oauth2/**").permitAll() // 허용 경로 설정
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/Customlogin") // 커스텀 로그인 페이지
//                        .defaultSuccessUrl("/dashboard", true) // 로그인 성공 시 리디렉션
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // 사용자 정보 서비스 등록
                        )
                );

        return http.build(); // SecurityFilterChain 반환
    }
}
