package com.example.longkathon.user.controller;

import com.example.longkathon.JWT.JWTUtil;
import com.example.longkathon.user.dto.UserRequest;
import com.example.longkathon.user.dto.UserResponse;
import com.example.longkathon.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final JWTUtil jwtUtil;

    @PostMapping("/name-email")
    public ResponseEntity<Long> saveNameAndEmail(@RequestBody UserRequest.UserNameEmailRequest req) {
        Long userId = userService.saveNameAndEmail(req);
        return ResponseEntity.ok(userId);
    }


    @GetMapping("/email")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        Long userId = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse.ReadUser> getUser(@PathVariable Long userId) {
        UserResponse.ReadUser response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/lands")
    public ResponseEntity<List<UserResponse.LandInfoResponse>> getLandsByUserId(@PathVariable Long userId) {
        List<UserResponse.LandInfoResponse> response = userService.getLandsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest req, HttpServletRequest request) {
        String token = userService.login(req.getEmail()); // JWT 발급

        boolean isLocal = request.getHeader("origin") != null && request.getHeader("origin").contains("localhost"); // 로컬 여부 확인
        boolean isSecure = !isLocal; // 로컬이 아니면 Secure 적용

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(isSecure)  // ✅ HTTPS 환경에서는 Secure 적용, 로컬에서는 제거
                .path("/")
                .sameSite(isSecure ? "None" : "Lax") // ✅ HTTPS에서는 "None", 로컬에서는 "Lax"
                .maxAge(7 * 24 * 60 * 60) // 7일 유지
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()) // ✅ 쿠키 추가
                .body("Login successful");
    }


    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", ""); // "Bearer " 제거
            Claims claims = jwtUtil.parseClaims(jwt);

            // JWT에서 "username" 값을 가져옴
            String username = claims.get("username", String.class);
            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found in token");
            }

            // username 기반으로 userId 조회
            Long userId = userService.getUserIdByUsername(username);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            return ResponseEntity.ok(Collections.singletonMap("userId", userId));
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
        } catch (MalformedJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        } catch (io.jsonwebtoken.security.SignatureException e) {  // 변경된 부분
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token signature");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error");
        }
    }
}
