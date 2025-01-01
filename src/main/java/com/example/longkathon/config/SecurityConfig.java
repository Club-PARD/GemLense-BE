package com.example.longkathon.config;

import com.example.longkathon.PrincipalOauth2UserService;
import com.example.longkathon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
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
                            // 로그인 성공 후 사용자 정보 가져오기
                            String username = authentication.getName();
                            Long userId = userRepository.findByName(username)
                                    .map(user -> user.getUserId())
                                    .orElseThrow(() -> new IllegalStateException("User not found"));

                            // 사용자 ID를 JSON으로 응답
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("{\"userId\": " + userId + "}");
                        })
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",   // 로컬 React
                "https://wecand.site",     // 배포된 React 및 Swagger UI
                "https://wecand.shop"      // 배포된 백엔드
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // HTTP 메서드 허용
        configuration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
        configuration.addExposedHeader("Authorization"); // 인증 헤더 노출
        configuration.setAllowCredentials(true); // 인증 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
