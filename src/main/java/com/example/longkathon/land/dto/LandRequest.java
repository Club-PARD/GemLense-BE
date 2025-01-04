package com.example.longkathon.land.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        private List<UrlPairRequest> urlPairs;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UrlPairRequest {
            private String url;
            private String urlName;
        }
    }
}
