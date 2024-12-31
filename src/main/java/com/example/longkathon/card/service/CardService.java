package com.example.longkathon.card.service;

import com.example.longkathon.S3Service;
import com.example.longkathon.card.dto.CardRequest;
import com.example.longkathon.card.dto.CardResponse;
import com.example.longkathon.card.entity.Card;
import com.example.longkathon.card.repository.CardRepository;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    @Transactional
    public void createCard(Long userId, CardRequest.CreateCardRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Card card = Card.builder()
                .name(req.getName())
                .gender(req.getGender())
                .identity(req.getIdentity())
                .major(req.getMajor())
                .age(req.getAge())
                .phone(req.getPhone())
                .email(req.getEmail())
                .communication(Optional.ofNullable(req.getCommunication()).orElseGet(ArrayList::new))
                .teamwork(Optional.ofNullable(req.getTeamwork()).orElseGet(ArrayList::new))
                .thinking(Optional.ofNullable(req.getThinking()).orElseGet(ArrayList::new))
                .role(Optional.ofNullable(req.getRole()).orElseGet(ArrayList::new))
                .conflictResolution(Optional.ofNullable(req.getConflictResolution()).orElseGet(ArrayList::new))
                .timePreference(Optional.ofNullable(req.getTimePreference()).orElseGet(ArrayList::new))
                .restPreference(Optional.ofNullable(req.getRestPreference()).orElseGet(ArrayList::new))
                .friendship(Optional.ofNullable(req.getFriendship()).orElseGet(ArrayList::new))
                .goal(Optional.ofNullable(req.getGoal()).orElseGet(ArrayList::new))
                .important(req.getImportant())
                .certificates(Optional.ofNullable(req.getCertificates()).orElseGet(ArrayList::new))
                .tools(Optional.ofNullable(req.getTools()).orElseGet(ArrayList::new))
                .awards(Optional.ofNullable(req.getAwards()).orElseGet(ArrayList::new))
                .portfolio(Optional.ofNullable(req.getPortfolio()).orElseGet(ArrayList::new))
                .additionalInfo(Optional.ofNullable(req.getAdditionalInfo()).orElseGet(ArrayList::new))
                .url(Optional.ofNullable(req.getUrl()).orElseGet(ArrayList::new))
                .user(user)
                .build();

        cardRepository.save(card);
    }

    @Transactional
    public void createCardWithFile(Long userId, CardRequest.CreateCardRequest req, MultipartFile file) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

            String fileUrl = s3Service.upload(file, "card-files");

            Card card = Card.builder()
                    .name(req.getName())
                    .gender(req.getGender())
                    .identity(req.getIdentity())
                    .major(req.getMajor())
                    .age(req.getAge())
                    .phone(req.getPhone())
                    .email(req.getEmail())
                    .url(List.of(fileUrl))
                    .user(user)
                    .build();

            cardRepository.save(card);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }


    public List<CardResponse> getCardsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return cardRepository.findByUser(user).stream()
                .map(card -> CardResponse.builder()
                        .cardId(card.getCardId())
                        .name(card.getName())
                        .gender(card.getGender())
                        .identity(card.getIdentity())
                        .major(card.getMajor())
                        .age(card.getAge())
                        .phone(card.getPhone())
                        .email(card.getEmail())
                        .communication(card.getCommunication())
                        .teamwork(card.getTeamwork())
                        .thinking(card.getThinking())
                        .role(card.getRole())
                        .conflictResolution(card.getConflictResolution())
                        .timePreference(card.getTimePreference())
                        .restPreference(card.getRestPreference())
                        .friendship(card.getFriendship())
                        .goal(card.getGoal())
                        .important(card.getImportant())
                        .certificates(card.getCertificates())
                        .tools(card.getTools())
                        .awards(card.getAwards())
                        .portfolio(card.getPortfolio())
                        .additionalInfo(card.getAdditionalInfo())
                        .url(card.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCard(Long userId, CardRequest.UpdateCardRequest req) {
        Card card = cardRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found for User with ID: " + userId));

        if (req.getCardName() != null) card.setName(req.getCardName());
        if (req.getGender() != null) card.setGender(req.getGender());
        if (req.getIdentity() != null) card.setIdentity(req.getIdentity());
        if (req.getMajor() != null) card.setMajor(req.getMajor());
        if (req.getAge() != null) card.setAge(req.getAge());
        if (req.getPhone() != null) card.setPhone(req.getPhone());
        if (req.getEmail() != null) card.setEmail(req.getEmail());
        if (req.getCommunication() != null) card.setCommunication(req.getCommunication());
        if (req.getTeamwork() != null) card.setTeamwork(req.getTeamwork());
        if (req.getThinking() != null) card.setThinking(req.getThinking());
        if (req.getRole() != null) card.setRole(req.getRole());
        if (req.getConflictResolution() != null) card.setConflictResolution(req.getConflictResolution());
        if (req.getTimePreference() != null) card.setTimePreference(req.getTimePreference());
        if (req.getRestPreference() != null) card.setRestPreference(req.getRestPreference());
        if (req.getFriendship() != null) card.setFriendship(req.getFriendship());
        if (req.getGoal() != null) card.setGoal(req.getGoal());
        if (req.getImportant() != null) card.setImportant(req.getImportant());
        if (req.getCertificates() != null) card.setCertificates(req.getCertificates());
        if (req.getTools() != null) card.setTools(req.getTools());
        if (req.getAwards() != null) card.setAwards(req.getAwards());
        if (req.getPortfolio() != null) card.setPortfolio(req.getPortfolio());
        if (req.getAdditionalInfo() != null) card.setAdditionalInfo(req.getAdditionalInfo());
        if (req.getUrl() != null) card.setUrl(req.getUrl());

        cardRepository.save(card);
    }
}