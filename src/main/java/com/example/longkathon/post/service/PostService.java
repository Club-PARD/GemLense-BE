package com.example.longkathon.post.service;

import com.example.longkathon.S3Service;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AppRepository appRepository;
    private final S3Service s3Service;

    @Transactional
    public Long createPost(Long ownerId, PostRequest postRequest) {
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .category(postRequest.getCategory())
                .date(postRequest.getDate())
                .member(postRequest.getMember())
                .url(postRequest.getUrl())
                .memo(postRequest.getMemo())
                .memo2(postRequest.getMemo2())
                .ownerId(ownerId)
                .createTime(LocalDateTime.now())
                .build();

        return postRepository.save(post).getPostId();
    }

    @Transactional
    public Long createPostWithImage(Long ownerId, PostRequest postRequest, MultipartFile image) {
        try {
            String imageUrl = s3Service.upload(image, "post-images");

            Post post = Post.builder()
                    .title(postRequest.getTitle())
                    .category(postRequest.getCategory())
                    .date(postRequest.getDate())
                    .member(postRequest.getMember())
                    .url(imageUrl)
                    .memo(postRequest.getMemo())
                    .memo2(postRequest.getMemo2())
                    .ownerId(ownerId)
                    .createTime(LocalDateTime.now())
                    .build();

            return postRepository.save(post).getPostId();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to S3", e);
        }
    }


    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(this::mapPostToResponse)
                .collect(Collectors.toList());
    }

    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        return mapPostToResponse(post);
    }

    public PostResponse getPostWithApplicants(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        List<App> applications = appRepository.findByPost_PostId(postId);

        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .date(post.getDate())
                .member(post.getMember())
                .url(post.getUrl())
                .memo(post.getMemo())
                .memo2(post.getMemo2())
                .ownerId(post.getOwnerId())
                .createTime(formatDateTime(post.getCreateTime()))
                .approvedCount(countApprovedApplicants(post.getPostId()))
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

    public List<PostResponse> getPostsByOwner(Long userId) {
        List<Post> posts = postRepository.findByOwnerId(userId);

        return posts.stream()
                .map(this::mapPostToResponse)
                .collect(Collectors.toList());
    }

    public List<PostResponse> getPostsAppliedByUser(Long userId) {
        List<Post> posts = postRepository.findByApplications_User_UserId(userId);

        return posts.stream()
                .map(this::mapPostToResponse)
                .collect(Collectors.toList());
    }

    private PostResponse mapPostToResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .date(post.getDate())
                .member(post.getMember())
                .url(post.getUrl())
                .memo(post.getMemo())
                .memo2(post.getMemo2())
                .ownerId(post.getOwnerId())
                .createTime(formatDateTime(post.getCreateTime()))
                .approvedCount(countApprovedApplicants(post.getPostId()))
                .build();
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime != null ? dateTime.format(formatter) : null;
    }

    private long countApprovedApplicants(Long postId) {
        return appRepository.countByPost_PostIdAndStatus(postId, "APPROVED");
    }
}
