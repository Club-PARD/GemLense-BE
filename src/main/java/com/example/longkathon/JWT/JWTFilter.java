package com.example.longkathon.JWT;

import com.example.longkathon.login.dto.CustomOAuth2User;
import com.example.longkathon.user.dto.UserRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String requestUri = request.getRequestURI();
//
//        // ✅ Swagger 관련 요청은 JWT 필터를 거치지 않도록 예외 처리
//        if (requestUri.startsWith("/swagger-ui") || requestUri.startsWith("/v3/api-docs")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // ✅ 로그인 및 OAuth2 관련 요청도 JWT 필터 예외 처리
//        if (requestUri.matches("^\\/login(?:\\/.*)?$") || requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // ✅ 쿠키에서 Authorization 토큰 찾기
//        String authorization = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("Authorization".equals(cookie.getName())) {
//                    authorization = cookie.getValue();
//                    break;
//                }
//            }
//        }
//
//        // ✅ Authorization 헤더 검증 (없으면 필터 통과)
//        if (authorization == null) {
//            System.out.println("token null");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // ✅ 토큰 검증
//        String token = authorization;
//        if (jwtUtil.isExpired(token)) {
//            System.out.println("token expired");
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // ✅ 토큰에서 username과 role 추출
//        String username = jwtUtil.getUsername(token);
//        String role = jwtUtil.getRole(token);
//
//        // ✅ UserDTO를 생성하여 값 설정
//        UserRequest u = new UserRequest();
//        u.setUsername(username);
//        u.setRole(role);
//
//        // ✅ 스프링 시큐리티 인증 토큰 생성
//        CustomOAuth2User customOAuth2User = new CustomOAuth2User(u);
//        Authentication authToken = new UsernamePasswordAuthenticationToken(
//                customOAuth2User, null, customOAuth2User.getAuthorities());
//
//        // ✅ 세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ 모든 요청을 허용하도록 설정
        filterChain.doFilter(request, response);
    }
}
