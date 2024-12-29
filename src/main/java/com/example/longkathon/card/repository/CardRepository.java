package com.example.longkathon.card.repository;

import com.example.longkathon.card.entity.Card;
import com.example.longkathon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByUser(User user);
    Optional<Card> findByUser_UserId(Long userId); // userId로 카드 조회
}
