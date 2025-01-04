package com.example.longkathon.land.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class LandResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandDetailsResponse {
        private Long landId;
        private String landName;
        private Long ownerId;
        private Integer maxMember;
        private Integer currentMember;
        private List<UrlPairResponse> urlPairs = new ArrayList<>();

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class UrlPairResponse {
            private String url;
            private String urlName;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandUserResponse {
        private Long userId;
        private String name;
        private String email;
        private String role; // "owner" or "member"
        private Integer num;
    }
}
