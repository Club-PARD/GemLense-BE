package com.example.longkathon.post.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String category;
    private String date; // 문자열 형식의 날짜
    private String member;
    private String url;
    private String memo;
    private String img;
    private Long selectCard;
}