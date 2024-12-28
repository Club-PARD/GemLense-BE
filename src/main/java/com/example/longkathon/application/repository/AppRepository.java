package com.example.longkathon.application.repository;

import com.example.longkathon.application.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRepository extends JpaRepository<App, Long> {
    List<App> findByPost_PostId(Long postId);
}
