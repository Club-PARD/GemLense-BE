package com.example.longkathon.user.service;

import com.example.longkathon.card.entity.Card;
import com.example.longkathon.landUser.entity.LandUser;
import com.example.longkathon.landUser.repository.LandUserRepository;
import com.example.longkathon.user.dto.UserRequest;
import com.example.longkathon.user.dto.UserResponse;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LandUserRepository landUserRepository;

    @Transactional
    public void saveNameAndEmail(UserRequest.UserNameEmailRequest req) {
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public UserResponse.ReadUser getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return UserResponse.ReadUser.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public List<UserResponse.LandInfoResponse> getLandsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        List<LandUser> landUsers = landUserRepository.findByUser(user);

        return landUsers.stream()
                .map(landUser -> UserResponse.LandInfoResponse.builder()
                        .landId(landUser.getLand().getLandId())
                        .landName(landUser.getLand().getLandName())
                        .role(landUser.getRole())
                        .build())
                .collect(Collectors.toList());
    }

}
