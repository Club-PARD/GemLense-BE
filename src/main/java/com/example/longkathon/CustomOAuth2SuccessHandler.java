package com.example.longkathon;
// CustomOAuth2SuccessHandler.java
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 ID 추출 (Google의 경우 'sub' 속성)
        String userId = oAuth2User.getAttribute("sub");

        if (userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID not found");
            return;
        }

        // JSON 응답 준비
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", userId);

        // 응답 타입 설정 및 JSON 작성
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(userInfo));

        // 또는 프론트엔드로 리디렉션
        // response.sendRedirect("https://wecand.site/home?userId=" + userId);
    }
}

