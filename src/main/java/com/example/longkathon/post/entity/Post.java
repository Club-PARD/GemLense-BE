package com.example.longkathon.post.entity;

import com.example.longkathon.application.entity.App;
import com.example.longkathon.land.entity.Land;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private String member;
    private String url;
    private String memo;
    private String img;
    private Long selectCard;
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<App> applications;

}
