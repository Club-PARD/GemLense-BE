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
    }
}
