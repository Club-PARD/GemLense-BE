package com.example.longkathon.post.service;

import com.example.longkathon.application.dto.AppResponse;
import com.example.longkathon.application.entity.App;
import com.example.longkathon.application.repository.AppRepository;
import com.example.longkathon.post.dto.PostRequest;
import com.example.longkathon.post.dto.PostResponse;
import com.example.longkathon.post.entity.Post;
import com.example.longkathon.post.repository.PostRepository;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AppRepository appRepository;

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
                .img(postRequest.getImg())
                .ownerId(ownerId)
                .createTime(LocalDateTime.now())
                .build();

        return postRepository.save(post).getPostId();
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
                .img(post.getImg())
                .ownerId(post.getOwnerId())
                .createTime(formatDateTime(post.getCreateTime()))
                .approvedCount(countApprovedApplicants(post.getPostId()))
                .totalApplicants(applications.size()) // 지원자 총 수 추가
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
        // 1) 유저가 지원한 App 목록을 전부 가져온다.
        List<App> apps = appRepository.findByUser_UserId(userId);

        // 2) 각 App에 연결된 Post와, 현재 App 상태(status)를 가져와서 PostResponse에 매핑한다.
        return apps.stream()
                .map(app -> PostResponse.builder()
                        .postId(app.getPost().getPostId())
                        .title(app.getPost().getTitle())
                        .category(app.getPost().getCategory())
                        // PostResponse에 있는 status 필드를 App의 상태로 채워준다.
                        .status(app.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    private PostResponse mapPostToResponse(Post post) {
        // User 엔티티에서 ownerName 가져오기
        String ownerName = userRepository.findById(post.getOwnerId())
                .map(User::getName) // User 엔티티의 name 필드
                .orElse("Unknown");

        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .date(post.getDate())
                .member(post.getMember())
                .url(post.getUrl())
                .memo(post.getMemo())
                .memo2(post.getMemo2())
                .img(post.getImg())
                .ownerId(post.getOwnerId())
                .ownerName(ownerName) // ownerName 설정
                .createTime(formatDateTime(post.getCreateTime()))
                .approvedCount(countApprovedApplicants(post.getPostId()))
                .totalApplicants(post.getApplications().size())
                .build();
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime != null ? dateTime.format(formatter) : null;
    }

    private long countApprovedApplicants(Long postId) {
        return appRepository.countByPost_PostIdAndStatus(postId, "APPROVED");
    }

    public List<Post> searchPosts(String keyword, String category) {
        if (category == null || category.isEmpty()) {
            return postRepository.findPostsByKeyword(keyword);
        }
        return postRepository.findPostsWithoutApplications(keyword, category);
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        post.setTitle(postRequest.getTitle());
        post.setCategory(postRequest.getCategory());
        post.setDate(postRequest.getDate());
        post.setMember(postRequest.getMember());
        post.setUrl(postRequest.getUrl());
        post.setMemo(postRequest.getMemo());
        post.setMemo2(postRequest.getMemo2());
        post.setImg(postRequest.getImg());

        postRepository.save(post);
        return mapPostToResponse(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        postRepository.delete(post);
    }


}
