package com.example.longkathon.user.repository;

import com.example.longkathon.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
    User findByUsername(String username);

    @Query("SELECT u.userId FROM User u WHERE u.username = :username")
    Optional<Long> findUserIdByUsername(String username);


}
