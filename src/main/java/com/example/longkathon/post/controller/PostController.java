package com.example.longkathon.post.controller;

import com.example.longkathon.post.dto.PostRequest;
import com.example.longkathon.post.dto.PostResponse;
import com.example.longkathon.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{userId}")
    public ResponseEntity<Long> createPost(@PathVariable Long userId, @RequestBody PostRequest postRequest) {
        Long postId = postService.createPost(userId, postRequest);
        return ResponseEntity.ok(postId);
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPostById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{postId}/with-applicants")
    public ResponseEntity<PostResponse> getPostWithApplicants(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPostWithApplicants(postId);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/owner/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsByOwner(@PathVariable Long userId) {
        List<PostResponse> posts = postService.getPostsByOwner(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/applied/{userId}")
    public ResponseEntity<List<PostResponse>> getPostsAppliedByUser(@PathVariable Long userId) {
        List<PostResponse> posts = postService.getPostsAppliedByUser(userId);
        return ResponseEntity.ok(posts);
    }
}