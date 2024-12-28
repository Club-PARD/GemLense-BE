package com.example.longkathon.application.service;

import com.example.longkathon.application.entity.App;
import com.example.longkathon.application.repository.AppRepository;
import com.example.longkathon.post.entity.Post;
import com.example.longkathon.post.repository.PostRepository;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
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

    public void applyToPost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        App application = App.builder()
                .user(user)
                .post(post)
                .status("pending") // 초기 상태는 "pending"
                .build();

        appRepository.save(application);
    }

    public List<App> getApplicationsByPost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        return appRepository.findByPost_PostId(postId);
    }
}
