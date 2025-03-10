package com.example.longkathon.card.entity;

import com.example.longkathon.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true) // unique 추가
    private User user;

    private String cardName;
    private String gender;
    private String identity;
    private String major;
    private Long age;
    private String phone;
    private String email;

    @ElementCollection
    @CollectionTable(name = "card_communication", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "communication_value")
    @Builder.Default
    private List<String> communication = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_teamwork", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "teamwork_value")
    @Builder.Default
    private List<String> teamwork = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_thinking", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "thinking_value")
    @Builder.Default
    private List<String> thinking = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_role", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "role_value")
    @Builder.Default
    private List<String> role = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_conflict_resolution", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "conflict_resolution_value")
    @Builder.Default
    private List<String> conflictResolution = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_time_preference", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "time_preference_value")
    @Builder.Default
    private List<String> timePreference = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_rest_preference", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "rest_preference_value")
    @Builder.Default
    private List<String> restPreference = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_friendship", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "friendship_value")
    @Builder.Default
    private List<String> friendship = new ArrayList<>();

    private String important;


    @ElementCollection
    @CollectionTable(name = "card_certificates", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "certificates_value")
    @Builder.Default
    private List<String> certificates = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_tools", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "tools_value")
    @Builder.Default
    private List<String> tools = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "card_awards", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "awards_value")
    @Builder.Default
    private List<String> awards = new ArrayList<>();

    private String additionalInfo;

    @Column(name = "url", length = 2083) // 컬럼 길이를 DB와 일치시킴
    private String url;


    @Column(name = "fileUrl", length = 2083) // 컬럼 길이를 DB와 일치시킴
    private String fileUrl;

}
