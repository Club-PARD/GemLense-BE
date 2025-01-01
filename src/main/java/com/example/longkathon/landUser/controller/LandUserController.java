package com.example.longkathon.landUser.controller;


import com.example.longkathon.land.service.LandService;
import com.example.longkathon.landUser.service.LandUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/landuser")
@CrossOrigin(origins = {"http://localhost:8080", "https://wecand.shop"})
public class LandUserController {
    private final LandUserService landUserService;
    private final LandService landService;


}
