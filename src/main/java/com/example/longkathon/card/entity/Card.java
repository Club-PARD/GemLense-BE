package com.example.longkathon.card.entity;

import com.example.longkathon.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

    private String name;
    private String gender;
    private String identity;
    private String major;
    private Long age;
    private String phone;
    private String email;

    @ElementCollection
    @CollectionTable(name = "card_communication", joinColumns = @JoinColumn(name = "card_id"))
    private List<String> communication;

    @ElementCollection
    @CollectionTable(name = "card_teamwork", joinColumns = @JoinColumn(name = "card_id"))
    private List<String> teamwork;

    @ElementCollection
    @CollectionTable(name = "card_thinking", joinColumns = @JoinColumn(name = "card_id"))
    private List<String> thinking;

    @ElementCollection
    @CollectionTable(name = "card_role", joinColumns = @JoinColumn(name = "card_id"))
    private List<String> role;

    @ElementCollection
    @CollectionTable(name = "card_conflict_resolution", joinColumns = @JoinColumn(name = "card_id"))
    private List<String> conflictResolution;

    private String timePreference;
    private String restPreference;
    private String friendship;
    private String goal;
    private String important;
    private String certificates;
    private String tools;
    private String awards;
    private String portfolio;
    private String additionalInfo;
    private String file;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
