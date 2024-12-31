package com.example.longkathon.post.repository;

import com.example.longkathon.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByOwnerId(Long ownerId);
    List<Post> findByApplications_User_UserId(Long userId); // 사용자가 신청한 글 조회
}
