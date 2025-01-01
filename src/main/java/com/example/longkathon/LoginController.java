package com.example.longkathon;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {
    @CrossOrigin(origins = "https://wecand.shop") // 특정 도메인 허용
    @GetMapping("/login")
    public String getData() {
        return "CORS 설정 완료!";
    }
}
