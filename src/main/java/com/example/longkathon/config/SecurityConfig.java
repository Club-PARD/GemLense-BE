package com.example.longkathon.config;

import com.example.longkathon.PrincipalOauth2UserService;
import com.example.longkathon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/oauth2/**", "/login", "/custom-login", "/swagger-ui/**", "/v3/api-docs/**",
                                "/home", "/recruiting", "/detail/**" // 인증 없이 접근 가능한 경로
                        ).permitAll()
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/home", true) // 기본 성공 리다이렉트 경로
                        .failureUrl("/login?error=true") // 실패 시 리다이렉트 경로
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(principalOauth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            // 로그인 성공 후 로직 처리
                            boolean hasCardInfo = checkCardInfo(authentication.getName());
                            if (hasCardInfo) {
                                response.sendRedirect("/home");
                            } else {
                                response.sendRedirect("/register/1");
                            }
                        })
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://wecand.shop")); // 허용할 도메인
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(List.of("*")); // 허용할 헤더
        configuration.setAllowCredentials(true); // 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 사용자 카드 정보 확인 로직 (예제)
    private boolean checkCardInfo(String name) {
        return userRepository.findByName(name)
                .map(user -> user.getCardInfo() != null) // 카드 정보가 존재하는지 확인
                .orElse(false); // 사용자가 없으면 false 반환
    }
}
