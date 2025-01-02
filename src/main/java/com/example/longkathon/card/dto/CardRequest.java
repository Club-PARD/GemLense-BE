package com.example.longkathon.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CardRequest {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCardRequest {
        private String cardName;
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
        private String important;
        private List<String> certificates;
        private List<String> tools;
        private List<String> awards;
        private String additionalInfo;
        private List<String> url;
        private String file;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCardRequest {
        private String cardName;
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
        private String important;
        private List<String> certificates;
        private List<String> tools;
        private List<String> awards;
        private List<String> portfolio;
        private String additionalInfo;
        private List<String> url;
        private String file;
    }
}
