package com.example.longkathon;

import com.example.longkathon.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository userRepository;

    public ApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user-id")
    public ResponseEntity<?> getUserId(Authentication authentication) {
        String username = authentication.getName();
        Long userId = userRepository.findByName(username)
                .map(user -> user.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok().body(Map.of("userId", userId));
    }
}

