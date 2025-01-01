package com.example.longkathon;

import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomOAuth2SuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 email 추출
        String userEmail = oAuth2User.getAttribute("email");

        if (userEmail == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User email not found");
            return;
        }

        // user 테이블에서 이메일로 사용자 검색
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);

        User user;
        if (optionalUser.isPresent()) {
            // 기존 사용자
            user = optionalUser.get();
        } else {
            // 새로운 사용자 생성 및 저장
            user = new User();
            user.setEmail(userEmail);
            user = userRepository.save(user);
        }

        // 사용자 ID 추출
        Long userId = user.getUserId();

        String html = "<html><body>"
                + "<script>"
                + "window.localStorage.setItem('userId', '" + userId + "');"
                + "window.location.href = 'https://wecand.site/home';"
                + "</script>"
                + "</body></html>";

        response.setContentType("text/html");
        response.getWriter().write(html);

//        // 쿠키 설정
//        Cookie userIdCookie = new Cookie("userId", String.valueOf(userId));
//        userIdCookie.setPath("/");
//        userIdCookie.setHttpOnly(true); // 보안을 위해 HTTP Only 설정
//        userIdCookie.setSecure(true); // HTTPS 사용 시 설정
//        userIdCookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효 기간 설정 (1일)
//        response.addCookie(userIdCookie);

//        // 리다이렉트
//        String redirectUrl = "https://wecand.shop/login/callback";
//        response.sendRedirect(redirectUrl);
    }
}
