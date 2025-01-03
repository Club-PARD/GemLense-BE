package com.example.longkathon.application.repository;

import com.example.longkathon.application.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {

    @Query("SELECT COUNT(a) FROM App a WHERE a.post.postId = :postId AND a.status = :status")
    long countByPost_PostIdAndStatus(@Param("postId") Long postId, @Param("status") String status);

    Optional<App> findByPost_PostIdAndUser_UserId(Long postId, Long userId);

    List<App> findByPost_PostId(Long postId);

    @Query("SELECT a FROM App a WHERE a.post.postId = :postId AND a.status = :status")
    List<App> findByPostIdAndStatus(@Param("postId") Long postId, @Param("status") String status);


}
