    package com.example.longkathon.user.controller;

    import com.example.longkathon.user.dto.UserRequest;
    import com.example.longkathon.user.dto.UserResponse;
    import com.example.longkathon.user.service.UserService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    //import org.springframework.security.core.annotation.AuthenticationPrincipal;
    //import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.web.bind.annotation.*;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/user")
    @RequiredArgsConstructor
    public class UserController {
        private final UserService userService;

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
    }   