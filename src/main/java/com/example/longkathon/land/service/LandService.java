package com.example.longkathon.land.service;

import com.example.longkathon.application.entity.App;
import com.example.longkathon.application.repository.AppRepository;
import com.example.longkathon.land.dto.LandRequest.CreateLandRequest;
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



    // 특정 팀원의 카드 조회 (가정: CardService를 통해 카드 조회)
    public Object getMemberCardByLandAndUser(Long landId, Long userId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        LandUser landUser = land.getLandUsers().stream()
                .filter(user -> user.getUser().getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User not found in this land."));

        // 카드 조회 서비스 호출 (CardService)
        return "CardService에서 해당 사용자의 카드 조회 로직 추가";
    }

    // URL 리스트 조회
    public List<String> getUrls(Long landId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        return land.getUrlList();
    }

    public List<String> getUrlNames(Long landId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        return land.getUrlList();
    }

    // URL 추가 (urlName)
    @Transactional
    public void addUrlName(Long landId, String urlName) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));
        land.getUrlNameList().add(urlName);
        landRepository.save(land);
    }

    // URL 추가 (url)
    @Transactional
    public void addUrl(Long landId, String url) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));
        land.getUrlList().add(url);
        landRepository.save(land);
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
                    .num(num++) // num 값 순차 증가
                    .build();
            landUserRepository.save(memberLandUser);
        }

        return savedLand.getLandId();
    }


}

