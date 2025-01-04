package com.example.longkathon.land.controller;

import com.example.longkathon.land.dto.LandRequest;
import com.example.longkathon.land.dto.LandResponse;
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
    public ResponseEntity<Long> createLandFromPost(@PathVariable Long postId, @RequestParam Long userId) {
        Long landId = landService.createLandFromPost(postId, userId);
        return ResponseEntity.ok(landId);
    }

    @GetMapping("/{landId}/members")
    public ResponseEntity<List<LandUserResponse>> getMembers(@PathVariable Long landId) {
        return ResponseEntity.ok(landService.getMembersByLand(landId));
    }

    @GetMapping("/{landId}/urlPairs")
    public ResponseEntity<List<LandResponse.LandDetailsResponse.UrlPairResponse>> getUrlPairs(
            @PathVariable Long landId
    ) {
        List<LandResponse.LandDetailsResponse.UrlPairResponse> responseList
                = landService.getUrlPairs(landId);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/{landId}/urlPairs")
    public ResponseEntity<Void> addUrlPairs(
            @PathVariable Long landId,
            @RequestBody List<LandRequest.CreateLandRequest.UrlPairRequest> urlPairs
    ) {
        landService.addUrlPairs(landId, urlPairs);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{landId}/update-members")
    public ResponseEntity<Void> updateLandMembers(
            @PathVariable Long landId,
            @RequestParam Long userId) {

        landService.updateLandMembers(landId, userId);
        return ResponseEntity.ok().build();
    }
}

