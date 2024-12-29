package com.example.longkathon.landUser.entity;

import com.example.longkathon.land.entity.Land;
import com.example.longkathon.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LandUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long landUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_id", nullable = false)
    private Land land;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String role; // "owner" or "member"
    private Integer sequence; // 팀 내 순서
    private Integer num; // Land 내 고유 번호 (1부터 순차 증가)
}


