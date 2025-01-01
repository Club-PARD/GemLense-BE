package com.example.longkathon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String getData() {
        return "CORS 설정 완료!";
    }
}
