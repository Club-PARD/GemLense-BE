package com.example.longkathon.config;

import com.example.longkathon.PrincipalOauth2UserService;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/**", "/login").permitAll() // OAuth2 및 /login 경로 허용
                        .anyRequest().authenticated() // 그 외 경로는 인증 필요
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/custom-login") // 사용자 정의 로그인 페이지 경로
                        .defaultSuccessUrl("/home") // 성공 후 리다이렉션 경로
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(principalOauth2UserService)
                        )
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://wecand.shop")); // 허용할 도메인
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.addFilter(corsConfig.corsFilter());
//        http.authorizeHttpRequests(au ->
//                au.anyRequest().permitAll());
//        http.oauth2Login(
//                oauth -> oauth
//                        .loginPage("/Customlogin")
//                        .defaultSuccessUrl("/home")
//                        .userInfoEndpoint(
//                                userInfo ->
//                                        userInfo.userService(principalOauth2UserService)
//                        )
//        );
//        return http.build();
//    }


