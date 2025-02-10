package com.example.longkathon.post.repository;

import com.example.longkathon.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT new Post(p.postId, p.title, p.category, p.date, p.member, p.url, p.memo, p.memo2, p.createTime, p.ownerId, p.img) " +
            "FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> findPostsWithoutApplications(@Param("keyword") String keyword);

    List<Post> findByOwnerId(Long ownerId);

}
