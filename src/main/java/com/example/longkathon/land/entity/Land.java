package com.example.longkathon.land.entity;

import com.example.longkathon.landUser.entity.LandUser;
import com.example.longkathon.post.entity.Post;
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
public class Land {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long landId;

    private String landName;

    private Long ownerId;

    private Integer maxMember;

    private Integer currentMember;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "land_urlNameList", joinColumns = @JoinColumn(name = "land_id"))
    private List<String> urlNameList = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "land_urlList", joinColumns = @JoinColumn(name = "land_id"))
    private List<String> urlList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LandUser> landUsers = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "post_id", unique = true, nullable = false) // 1대1 관계 설정
    private Post post;

    public void updateCurrentMember() {

        this.currentMember = landUsers.size();
    }
}
