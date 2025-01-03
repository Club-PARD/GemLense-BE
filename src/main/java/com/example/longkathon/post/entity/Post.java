package com.example.longkathon.post.entity;

import com.example.longkathon.application.entity.App;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String category;
    private String date;
    private Long member;
    private String url;
    private String memo;
    private String memo2;
    private LocalDateTime createTime;
    private Long ownerId;
    private String img;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<App> applications;
}
