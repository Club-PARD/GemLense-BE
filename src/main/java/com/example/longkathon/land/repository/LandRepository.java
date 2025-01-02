package com.example.longkathon.land.repository;

import com.example.longkathon.land.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LandRepository extends JpaRepository<Land, Long> {
    Optional<Land> findByPost_PostId(Long postId); // Post ID로 Land 조회
    @Query("SELECT l.currentMember FROM Land l WHERE l.landId = :landId")
    Integer findCurrentMemberByLandId(@Param("landId") Long landId);
}
