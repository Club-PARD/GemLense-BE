package com.example.longkathon.application.entity;

import com.example.longkathon.post.entity.Post;
import com.example.longkathon.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    @JsonBackReference // 직렬화에서 Post 제외
    private Post post;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private String status; // 예: "pending", "approved", "rejected"
}
