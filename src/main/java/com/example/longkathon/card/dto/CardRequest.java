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
        private List<String> timePreference;
        private List<String> restPreference;
        private List<String> friendship; // 수정: String -> List<String>
        private List<String> goal; // 수정: String -> List<String>
        private String important;
        private List<String> certificates; // 수정: String -> List<String>
        private List<String> tools; // 수정: String -> List<String>
        private List<String> awards; // 수정: String -> List<String>
        private List<String> portfolio; // 수정: String -> List<String>
        private List<String> additionalInfo; // 수정: String -> List<String>
        private List<String> url; // 수정: String -> List<String>
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
        private List<String> goal; // 수정: String -> List<String>
        private String important;
        private List<String> certificates; // 수정: String -> List<String>
        private List<String> tools; // 수정: String -> List<String>
        private List<String> awards; // 수정: String -> List<String>
        private List<String> portfolio; // 수정: String -> List<String>
        private List<String> additionalInfo; // 수정: String -> List<String>
        private List<String> url; // 수정: String -> List<String>
    }
}
