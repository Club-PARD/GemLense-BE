    package com.example.longkathon.user.controller;

    import com.example.longkathon.JWT.JWTUtil;
    import com.example.longkathon.user.dto.UserRequest;
    import com.example.longkathon.user.dto.UserResponse;
    import com.example.longkathon.user.service.UserService;
    import io.jsonwebtoken.Claims;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseCookie;
    import org.springframework.http.ResponseEntity;
    //import org.springframework.security.core.annotation.AuthenticationPrincipal;
    //import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.web.bind.annotation.*;

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
        public ResponseEntity<?> login(@RequestBody UserRequest req) {
            String token = userService.login(req.getEmail() ); // 로그인 후 JWT 발급

            // 쿠키 생성 (HttpOnly, Secure 설정)
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)  // XSS 공격 방지
                    .secure(false)    // HTTPS 사용 시 true로 변경
                    .path("/")        // 모든 요청에서 쿠키 사용 가능
                    .maxAge(7 * 24 * 60 * 60) // 7일 유지
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("Login successful");
        }

        public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token) {
            try {
                String jwt = token.replace("Bearer ", ""); // "Bearer " 제거
                Claims claims = jwtUtil.parseClaims(jwt);
                String email = claims.get("username", String.class); // JWT에서 email 추출

                if (email == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not found in token");
                }

                Long userId = userService.getUserIdByEmail(email); // 이메일 기반으로 userId 조회
                if (userId == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }

                return ResponseEntity.ok(Collections.singletonMap("userId", userId));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        }

    }   