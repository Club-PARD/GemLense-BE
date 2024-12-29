package com.example.longkathon.land.controller;

import com.example.longkathon.land.dto.LandResponse.LandUserResponse;
import com.example.longkathon.land.service.LandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/land")
@RequiredArgsConstructor
public class LandController {

    private final LandService landService;

    @PostMapping("/{postId}/create")
    public ResponseEntity<Long> createLandFromPost(@PathVariable Long postId, @RequestParam Long ownerId) {
        Long landId = landService.createLandFromPost(postId, ownerId);
        return ResponseEntity.ok(landId);
    }

    @GetMapping("/{landId}/members")
    public ResponseEntity<List<LandUserResponse>> getMembers(@PathVariable Long landId) {
        return ResponseEntity.ok(landService.getMembersByLand(landId));
    }

    @GetMapping("/{landId}/urls")
    public ResponseEntity<List<String>> getUrls(@PathVariable Long landId) {
        return ResponseEntity.ok(landService.getUrls(landId));
    }

    @PostMapping("/{landId}/url")
    public ResponseEntity<Void> addUrl(@PathVariable Long landId, @RequestBody String url) {
        landService.addUrl(landId, url);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{landId}/urlName")
    public ResponseEntity<Void> addUrlName(@PathVariable Long landId, @RequestBody String urlName) {
        landService.addUrlName(landId, urlName);
        return ResponseEntity.ok().build();
    }
}
