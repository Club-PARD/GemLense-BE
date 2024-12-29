package com.example.longkathon.application.repository;

import com.example.longkathon.application.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppRepository extends JpaRepository<App, Long> {

    // postId와 userId로 Application 찾기
    Optional<App> findByPost_PostIdAndUser_UserId(Long postId, Long userId);

    // 특정 Post의 모든 Application 조회
    List<App> findByPost_PostId(Long postId);

    // JPQL로 Post ID와 Status 기준으로 Application 조회 (승인된 사용자 찾기)
    @Query("SELECT a FROM App a WHERE a.post.postId = :postId AND a.status = :status")
    List<App> findByPostIdAndStatus(@Param("postId") Long postId, @Param("status") String status);
}
