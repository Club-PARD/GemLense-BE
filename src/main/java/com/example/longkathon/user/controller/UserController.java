package com.example.longkathon.user.controller;

import com.example.longkathon.user.dto.UserRequest;
import com.example.longkathon.user.dto.UserResponse;
import com.example.longkathon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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