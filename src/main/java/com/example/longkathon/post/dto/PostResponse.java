
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

    private List<AppResponse> applicants;

    private String imageUrl;

    private Long approvedCount;


    // 특정 필드만 초기화하는 메서드 추가
    public static PostResponse fromAppliedPost(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .category(post.getCategory())
                .ownerId(post.getOwnerId())
                .build();
    }
}
