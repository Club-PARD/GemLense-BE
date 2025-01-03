package com.example.longkathon.user.service;

import com.example.longkathon.application.repository.AppRepository;
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
    private final AppRepository appRepository;

    @Transactional
    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getUserId)
                .orElse(null);
    }

    @Transactional
    public Long saveNameAndEmail(UserRequest.UserNameEmailRequest req) {
        // 유효성 검사 (예: null 체크, 이메일 형식 확인 등)
        if (req.getName() == null || req.getEmail() == null) {
            throw new IllegalArgumentException("Name or email cannot be null");
        }

        // User 엔티티 생성 및 저장
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .build();

        User savedUser = userRepository.save(user);
        return savedUser.getUserId();
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

    @Transactional
    public List<UserResponse.LandInfoResponse> getLandsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        List<LandUser> landUsers = landUserRepository.findByUser(user);

        return landUsers.stream()
                .map(landUser -> {
                    Long landId = landUser.getLand().getLandId();
                    long countMember = countApprovedApplicants(landId) + 1; // 승인된 지원자 수에 1을 더함
                    return UserResponse.LandInfoResponse.builder()
                            .landId(landId)
                            .landName(landUser.getLand().getLandName())
                            .role(landUser.getRole())
                            .countMember(countMember) // 응답 객체에 countMember 추가
                            .build();
                })
                .collect(Collectors.toList());
    }

    private long countApprovedApplicants(Long landId) {
        return appRepository.countByPost_PostIdAndStatus(landId, "{\"status\":\"수락\"}");
    }

}
