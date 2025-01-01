package com.example.longkathon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String home() {
        return "home"; // Mustache 템플릿: templates/home.html
    }
}

