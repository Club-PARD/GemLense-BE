package com.example.longkathon.land.service;

import com.example.longkathon.application.entity.App;
import com.example.longkathon.application.repository.AppRepository;
import com.example.longkathon.land.dto.LandRequest;
import com.example.longkathon.land.dto.LandRequest.CreateLandRequest;
import com.example.longkathon.land.dto.LandResponse;
import com.example.longkathon.land.dto.LandResponse.LandDetailsResponse;
import com.example.longkathon.land.dto.LandResponse.LandUserResponse;
import com.example.longkathon.land.entity.Land;
import com.example.longkathon.land.repository.LandRepository;
import com.example.longkathon.landUser.entity.LandUser;
import com.example.longkathon.landUser.repository.LandUserRepository;
import com.example.longkathon.post.entity.Post;
import com.example.longkathon.post.repository.PostRepository;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class LandService {

    private final LandRepository landRepository;
    private final LandUserRepository landUserRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AppRepository appRepository;

    public List<LandUserResponse> getMembersByLand(Long landId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        return land.getLandUsers().stream()
                .map(landUser -> LandUserResponse.builder()
                        .userId(landUser.getUser().getUserId()) // 유저 ID
                        .name(landUser.getUser().getName())     // 유저 이름 추가
                        .email(landUser.getUser().getEmail())   // 유저 이메일
                        .role(landUser.getRole())              // 역할
                        .num(landUser.getNum())                // 고유 번호
                        .build())
                .collect(Collectors.toList());
    }



    @Transactional
    public List<LandResponse.LandDetailsResponse.UrlPairResponse> getUrlPairs(Long landId) {
        // 1) Land 엔티티 조회
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        // 2) Land가 가진 urlPairs(엔티티) -> Response DTO 변환
        //    (Land.UrlPair -> UrlPairResponse)
        return land.getUrlPairs().stream()
                .map(pair -> new LandResponse.LandDetailsResponse.UrlPairResponse(
                        pair.getUrl(),
                        pair.getUrlName()
                ))
                .collect(Collectors.toList());
    }

    /**
     * (url, urlName) 쌍들 여러 개 추가
     */
    public void addUrlPairs(Long landId, List<LandRequest.CreateLandRequest.UrlPairRequest> urlPairRequests) {
        // 1) Land 엔티티 조회
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        // 2) Request DTO -> 엔티티 변환 후 리스트에 추가
        //    (UrlPairRequest -> Land.UrlPair)
        for (LandRequest.CreateLandRequest.UrlPairRequest req : urlPairRequests) {
            land.getUrlPairs().add(
                    new Land.UrlPair(req.getUrl(), req.getUrlName())
            );
        }
        // 영속성 컨텍스트로 관리 중이므로, 명시적 save() 없이도 자동 반영
    }

    @Transactional
    public Long createLandFromPost(Long postId, Long ownerId) {
        // 1. Post 검증
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        if (!post.getOwnerId().equals(ownerId)) {
            throw new IllegalArgumentException("Only the owner of the post can create a land.");
        }

        // 2. 승인된 사용자 조회
        List<App> approvedApplications = appRepository.findByPostIdAndStatus(postId, "APPROVED");

        if (approvedApplications.isEmpty()) {
            throw new IllegalArgumentException("No approved users found for this post.");
        }

        // 3. Land 생성
        Land land = Land.builder()
                .landName(post.getTitle()) // Post 제목을 Land 이름으로 사용
                .ownerId(ownerId)
                .maxMember(approvedApplications.size() + 1) // 승인된 유저 + 방장
                .currentMember(1) // 방장은 기본 포함
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(30)) // 기본값
                .post(post)
                .build();

        Land savedLand = landRepository.save(land);

        // 4. 방장 추가
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with ID: " + ownerId));

        LandUser ownerLandUser = LandUser.builder()
                .land(savedLand)
                .user(owner)
                .role("owner")
                .sequence(1) // 방장은 항상 첫 번째
                .num(1) // 방장은 항상 num = 1
                .build();
        landUserRepository.save(ownerLandUser);

        // 5. 승인된 사용자 추가
        int sequence = 2; // 방장 다음 순서
        int num = 2;      // 방장 다음 num 값
        for (App app : approvedApplications) {
            LandUser memberLandUser = LandUser.builder()
                    .land(savedLand)
                    .user(app.getUser())
                    .role("member")
                    .sequence(sequence++)
                    .num(num++)
                    .build();
            landUserRepository.save(memberLandUser);
        }

        return savedLand.getLandId();
    }


    @Transactional
    public void updateLandMembers(Long landId, Long userId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        if (!land.getOwnerId().equals(userId)) {
            throw new IllegalArgumentException("Only the owner can update the land members.");
        }

        // 기존 멤버 삭제 (리스트 비우기)
        land.getLandUsers().clear();
        landRepository.save(land); // 연관된 LandUser 삭제

        // 새로운 멤버 추가
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with ID: " + userId));
        LandUser ownerLandUser = LandUser.builder()
                .land(land)
                .user(owner)
                .role("owner")
                .sequence(1)
                .num(1)
                .build();

        List<LandUser> newMembers = new ArrayList<>();
        newMembers.add(ownerLandUser);

        List<App> approvedApplications = appRepository.findByPostIdAndStatus(land.getPost().getPostId(), "APPROVED");
        final int[] num = {2};
        approvedApplications.forEach(app -> {
            User user = app.getUser();
            LandUser member = LandUser.builder()
                    .land(land)
                    .user(user)
                    .role("member")
                    .sequence(num[0])
                    .num(num[0]++)
                    .build();
            newMembers.add(member);
        });

        landUserRepository.saveAll(newMembers);
    }


}

