package com.example.longkathon.application.controller;

import com.example.longkathon.application.entity.App;
import com.example.longkathon.application.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;

    @PostMapping
    public ResponseEntity<Void> applyToPost(@RequestParam Long userId, @RequestParam Long postId) {
        appService.applyToPost(userId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<App>> getApplicationsByPost(@PathVariable Long postId) {
        List<App> applications = appService.getApplicationsByPost(postId);
        return ResponseEntity.ok(applications);
    }
}
