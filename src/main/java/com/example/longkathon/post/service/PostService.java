
package com.example.longkathon.post.service;

import com.example.longkathon.application.dto.AppResponse;
import com.example.longkathon.application.entity.App;
import com.example.longkathon.application.repository.AppRepository;
import com.example.longkathon.post.dto.PostRequest;
import com.example.longkathon.post.dto.PostResponse;
import com.example.longkathon.post.entity.Post;
import com.example.longkathon.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AppRepository appRepository; // 추가

    @Transactional
    public Long createPost(Long ownerId, PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .category(postRequest.getCategory())
                .date(postRequest.getDate())
                .member(postRequest.getMember())
                .url(postRequest.getUrl())
                .memo(postRequest.getMemo())
                .img(postRequest.getImg())
                .selectCard(postRequest.getSelectCard())
                .ownerId(ownerId) // 방장 ID 설정
                .createTime(LocalDateTime.now())
                .build();

        return postRepository.save(post).getPostId();
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return posts.stream()
                .map(post -> PostResponse.builder()
                        .postId(post.getPostId())
                        .title(post.getTitle())
                        .category(post.getCategory())
                        .date(post.getDate())
                        .member(post.getMember())
                        .url(post.getUrl())
                        .memo(post.getMemo())
                        .img(post.getImg())
                        .selectCard(post.getSelectCard())
                        .ownerId(post.getOwnerId()) // 방장 ID 포함
                        .createTime(post.getCreateTime().format(formatter))
                        .build())
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .date(post.getDate())
                .member(post.getMember())
                .url(post.getUrl())
                .memo(post.getMemo())
                .img(post.getImg())
                .selectCard(post.getSelectCard())
                .ownerId(post.getOwnerId()) // 방장 ID 포함
                .createTime(post.getCreateTime().format(formatter))
                .build();
    }

    public PostResponse getPostWithApplicants(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        // appRepository를 통해 데이터를 조회
        List<App> applications = appRepository.findByPost_PostId(postId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .date(post.getDate())
                .member(post.getMember())
                .url(post.getUrl())
                .memo(post.getMemo())
                .img(post.getImg())
                .selectCard(post.getSelectCard())
                .ownerId(post.getOwnerId()) // 방장 ID 포함
                .createTime(post.getCreateTime().format(formatter))
                .applicants(applications.stream()
                        .map(app -> AppResponse.builder()
                                .applicationId(app.getApplicationId())
                                .userId(app.getUser().getUserId())
                                .userName(app.getUser().getName())
                                .userEmail(app.getUser().getEmail())
                                .status(app.getStatus())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
