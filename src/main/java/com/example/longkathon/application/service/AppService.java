package com.example.longkathon.application.service;

import com.example.longkathon.application.dto.AppResponse;
import com.example.longkathon.application.entity.App;
import com.example.longkathon.application.repository.AppRepository;
import com.example.longkathon.post.entity.Post;
import com.example.longkathon.post.repository.PostRepository;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 신청 상태 처리 (postId와 userId로 찾기)

    @Transactional
    public void handleApplication(Long postId, Long userId, String status) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        // postId와 userId로 Application 찾기
        App application = appRepository.findByPost_PostIdAndUser_UserId(postId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found for the given post and user."));

        application.setStatus(status);
        appRepository.save(application);
    }
    // 사용자가 Post에 신청
    @Transactional
    public void applyToPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        App application = App.builder()
                .user(user)
                .post(post)
                .status("PENDING")
                .build();

        appRepository.save(application);
    }

    // 특정 Post의 모든 Application 조회
    @Transactional
    public List<AppResponse> getApplicationsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        List<App> applications = appRepository.findByPost_PostId(postId);

        return applications.stream()
                .map(app -> AppResponse.builder()
                        .applicationId(app.getApplicationId())
                        .userId(app.getUser().getUserId())
                        .userName(app.getUser().getName())
                        .userEmail(app.getUser().getEmail())
                        .status(app.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    // 승인된 사용자 조회
    public List<App> getApprovedApplications(Long postId) {
        return appRepository.findByPostIdAndStatus(postId, "\"APPROVED\"");
    }


}
