package com.example.longkathon.land.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class LandRequest {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateLandRequest {
        private String landName;
        private Long ownerId;
        private Integer maxMember;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }
}
