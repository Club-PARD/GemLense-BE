package com.example.longkathon.post.dto;

import com.example.longkathon.application.dto.AppResponse;
import com.example.longkathon.post.entity.Post;
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
    private Long member;
    private String url;
    private String memo;
    private String memo2;
    private String img;
    private String createTime; // 문자열 형식의 생성 시간
    private Long ownerId; // 방장 ID
    private String ownerName;

    private List<AppResponse> applicants;

    private long approvedCount;   // 승인된 지원자 수
    private long totalApplicants; // 지원자 총 수

    private String status; // App의 status 추가

    // Constructor
    public PostResponse(Long postId, String title, String category, String status) {
        this.postId = postId;
        this.title = title;
        this.category = category;
        this.status = status;
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }
}


