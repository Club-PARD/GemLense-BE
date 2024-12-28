package com.example.longkathon.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppResponse {
    private Long applicationId;
    private Long userId;
    private String userName;
    private String userEmail;
    private String status;
}
