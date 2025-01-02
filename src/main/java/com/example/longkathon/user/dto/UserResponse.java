package com.example.longkathon.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReadUser {
        private String name;
        private String email;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandInfoResponse {
        private Long landId;
        private String landName;
        private String role;
        private long countMember;

    }


}