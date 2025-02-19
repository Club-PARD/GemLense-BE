package com.example.longkathon.post.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String category;
    private String date;
    private Long member;
    private String url;
    private String memo;
    private String memo2;
    private String img;
}