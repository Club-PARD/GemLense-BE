package com.example.longkathon.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {
    private Long cardId;
    private String name;
    private String gender;
    private String identity;
    private String major;
    private Long age;
    private String phone;
    private String email;
    private List<String> communication;
    private List<String> teamwork;
    private List<String> thinking;
    private List<String> role;
    private List<String> conflictResolution;
    private List<String> timePreference;
    private List<String> restPreference;
    private List<String> friendship;
    private List<String> goal;
    private String important;
    private List<String> certificates;
    private List<String> tools;
    private List<String> awards;
    private List<String> portfolio;
    private List<String> additionalInfo;
    private List<String> url;
}
