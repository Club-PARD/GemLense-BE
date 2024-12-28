package com.example.longkathon.landUser.repository;

import com.example.longkathon.landUser.entity.LandUser;
import com.example.longkathon.land.entity.Land;
import com.example.longkathon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandUserRepository extends JpaRepository<LandUser, Long> {
    List<LandUser> findByUser(User user);
    List<LandUser> findByLand(Land land);
}
