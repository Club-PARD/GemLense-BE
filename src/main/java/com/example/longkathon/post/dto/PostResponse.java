
package com.example.longkathon.post.dto;

import com.example.longkathon.application.dto.AppResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostResponse {
    private Long postId;
    private String title;
    private String category;
    private String date; // 문자열 형식의 날짜
    private String member;
    private String url;
    private String memo;
    private String img;
    private Long selectCard;
    private String createTime; // 문자열 형식의 생성 시간
    private Long ownerId; // 방장 ID

    private List<AppResponse> applicants;
}