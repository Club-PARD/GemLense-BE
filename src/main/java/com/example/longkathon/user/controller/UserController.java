package com.example.longkathon.user.controller;

import com.example.longkathon.user.dto.UserRequest;
import com.example.longkathon.user.dto.UserResponse;
import com.example.longkathon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "https://wecand.shop"})
public class UserController {
    private final UserService userService;

//    @PostMapping("/name-email")
//    public ResponseEntity<Void> saveNameAndEmail(@RequestBody UserRequest.UserNameEmailRequest req) {
//        userService.saveNameAndEmail(req);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/user")
    public Map<String, String> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, String> userInfo = new HashMap<>();
        if (principal != null) {
            userInfo.put("userId", principal.getAttribute("sub")); // 사용자 ID
        } else {
            userInfo.put("error", "User not authenticated");
        }
        return userInfo;
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