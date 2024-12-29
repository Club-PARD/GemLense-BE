//package com.example.longkathon;
//
//import com.example.longkathon.user.entity.User;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class MainController {
//    @GetMapping("/main")
//    public String mainPage(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        model.addAttribute("userName", user.getName());
//        return "main"; // main.html
//    }
//}
//
