package com.example.longkathon.landUser.service;

import com.example.longkathon.land.entity.Land;
import com.example.longkathon.land.repository.LandRepository;
import com.example.longkathon.landUser.entity.LandUser;
import com.example.longkathon.landUser.repository.LandUserRepository;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LandUserService {
    private final LandUserRepository landUserRepository;
    private final LandRepository landRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addUserToLand(Long landId, Long userId, String role) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (land.getCurrentMember() >= land.getMaxMember()) {
            throw new IllegalStateException("Max member limit reached for this land.");
        }

        LandUser landUser = LandUser.builder()
                .land(land)
                .user(user)
                .role(role) // e.g., "member"
                .build();

        land.getLandUsers().add(landUser);
        land.setCurrentMember(land.getCurrentMember() + 1);

        landUserRepository.save(landUser);
    }

}
