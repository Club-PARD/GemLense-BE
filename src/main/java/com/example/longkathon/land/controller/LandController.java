package com.example.longkathon.land.controller;

import com.example.longkathon.land.dto.LandRequest.CreateLandRequest;
import com.example.longkathon.land.dto.LandResponse.LandDetailsResponse;
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

    @PostMapping
    public ResponseEntity<Void> createLand(@RequestBody CreateLandRequest createLandRequest) {
        landService.createLand(createLandRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{landId}/addUser")
    public ResponseEntity<Void> addUserToLand(@PathVariable Long landId, @RequestParam Long userId) {
        landService.addUserToLand(landId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{landId}/details")
    public ResponseEntity<LandDetailsResponse> getLandDetails(@PathVariable Long landId) {
        return ResponseEntity.ok(landService.getLandDetails(landId));
    }

    @GetMapping("/{landId}/members")
    public ResponseEntity<List<LandUserResponse>> getMembersByLand(@PathVariable Long landId) {
        return ResponseEntity.ok(landService.getMembersByLand(landId));
    }

    @PostMapping("/{landId}/urlName")
    public ResponseEntity<Void> addUrlName(@PathVariable Long landId, @RequestBody String urlName) {
        landService.addUrlName(landId, urlName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{landId}/url")
    public ResponseEntity<Void> addUrl(@PathVariable Long landId, @RequestBody String url) {
        landService.addUrl(landId, url);
        return ResponseEntity.ok().build();
    }
}
