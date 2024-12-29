package com.example.longkathon.land.repository;

import com.example.longkathon.land.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandRepository extends JpaRepository<Land, Long> {
    Optional<Land> findByPost_PostId(Long postId); // Post ID로 Land 조회
}
