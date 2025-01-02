package com.example.longkathon.user.service;

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

    // 구글 로그인 사용자 저장 또는 업데이트
    @Transactional
    public User saveOrUpdateGoogleUser(String email, String name) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .email(email)
                        .name(name)
                        .build()));
    }

    public void saveNameAndEmail(UserRequest.UserNameEmailRequest req) {
        // 유효성 검사 (예: null 체크, 이메일 형식 확인 등)
        if (req.getName() == null || req.getEmail() == null) {
            throw new IllegalArgumentException("Name or email cannot be null");
        }

        // User 엔티티 생성 및 저장
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .build();

        userRepository.save(user);
    }

    // 특정 유저 정보 조회
    @Transactional
    public UserResponse.ReadUser getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return UserResponse.ReadUser.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    // 특정 유저가 속한 Land 정보 조회
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
