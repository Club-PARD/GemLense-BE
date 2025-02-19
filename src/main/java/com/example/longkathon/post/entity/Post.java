package com.example.longkathon.post.entity;

import com.example.longkathon.application.entity.App;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    public Post(Long postId, String title, String category, String date, Long member,
                String url, String memo, String memo2, LocalDateTime createTime, Long ownerId, String img) {
        this.postId = postId;
        this.title = title;
        this.category = category;
        this.date = date;
        this.member = member;
        this.url = url;
        this.memo = memo;
        this.memo2 = memo2;
        this.createTime = createTime;
        this.ownerId = ownerId;
        this.img = img;
    }


}
