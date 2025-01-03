package com.example.longkathon.application.controller;

import com.example.longkathon.application.dto.AppResponse;
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

    // 신청 상태 처리
    @PatchMapping("/{postId}/{appUserId}")
    public ResponseEntity<Void> handleApplication(
            @PathVariable Long postId,
            @PathVariable Long appUserId,
            @RequestBody String status
    ) {
        appService.handleApplication(postId, appUserId, status);
        return ResponseEntity.ok().build();
    }

    // 사용자가 Post에 신청
    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Void> applyToPost(@PathVariable Long userId, @PathVariable Long postId) {
        appService.applyToPost(userId, postId);
        return ResponseEntity.ok().build();
    }

    // 특정 Post의 모든 Application 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<AppResponse>> getApplicationsByPost(@PathVariable Long postId) {
        List<AppResponse> applications = appService.getApplicationsByPost(postId);
        return ResponseEntity.ok(applications);
    }
}
