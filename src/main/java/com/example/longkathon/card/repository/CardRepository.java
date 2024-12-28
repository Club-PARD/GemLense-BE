package com.example.longkathon.card.repository;

import com.example.longkathon.card.entity.Card;
import com.example.longkathon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUser(User user);
}
