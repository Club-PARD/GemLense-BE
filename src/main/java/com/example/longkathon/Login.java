package com.example.longkathon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login {
    @GetMapping("/Customlogin")
    public String Customlogin() {
        return "Customlogin";
    }
}
