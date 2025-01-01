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

import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.addFilter(corsConfig.corsFilter());
        http.authorizeHttpRequests(au ->
                au.anyRequest().permitAll());
        http.oauth2Login(
                oauth -> oauth
                        .loginPage("/Customlogin")
                        .defaultSuccessUrl("/home")
                        .userInfoEndpoint(
                                userInfo ->
                                        userInfo.userService(principalOauth2UserService)
                        )
        );
        return http.build();
    }
}


//package com.example.longkathon.config;
//
//import com.example.longkathon.PrincipalOauth2UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final PrincipalOauth2UserService principalOauth2UserService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/public/**").permitAll() // "/public/**"는 인증 없이 접근 가능
//                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
//                )
//                .oauth2Login(oauth -> oauth
//                        .loginPage("/Customlogin") // 커스텀 로그인 페이지 경로
//                        .defaultSuccessUrl("/home") // 로그인 성공 후 이동 경로
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(principalOauth2UserService) // OAuth2 사용자 서비스 설정
//                        )
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://wecand.site"));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
