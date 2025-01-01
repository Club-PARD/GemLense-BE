package com.example.longkathon.card.controller;

import com.example.longkathon.card.dto.CardRequest;
import com.example.longkathon.card.dto.CardResponse;
import com.example.longkathon.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:8080", "https://wecand.shop"})
public class CardController {
    private final CardService cardService;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> createCard(@PathVariable Long userId, @RequestBody CardRequest.CreateCardRequest req) {
        cardService.createCard(userId, req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CardResponse>> getCardsByUserId(@PathVariable Long userId) {
        List<CardResponse> response = cardService.getCardsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateCardByUserId(
            @PathVariable Long userId,
            @RequestBody CardRequest.UpdateCardRequest req
    ) {
        cardService.updateCard(userId, req);
        return ResponseEntity.ok().build();
    }

}
