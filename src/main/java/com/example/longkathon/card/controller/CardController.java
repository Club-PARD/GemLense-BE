// src/main/java/com/example/longkathon/card/controller/CardController.java
package com.example.longkathon.card.controller;

import com.example.longkathon.card.dto.CardRequest;
import com.example.longkathon.card.dto.CardResponse;
import com.example.longkathon.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> createCard(@PathVariable Long userId, @RequestBody CardRequest.CreateCardRequest req) {
        cardService.createCard(userId, req);
        return ResponseEntity.ok("Card created successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CardResponse>> getCards(@PathVariable Long userId) {
        List<CardResponse> cards = cardService.getCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateCard(@PathVariable Long userId, @RequestBody CardRequest.UpdateCardRequest req) {
        cardService.updateCard(userId, req);
        return ResponseEntity.ok("Card updated successfully");
    }
}
