package com.example.longkathon.card.controller;

import com.example.longkathon.card.dto.CardRequest;
import com.example.longkathon.card.dto.CardResponse;
import com.example.longkathon.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // 클라이언트 주소 설정
@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
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
