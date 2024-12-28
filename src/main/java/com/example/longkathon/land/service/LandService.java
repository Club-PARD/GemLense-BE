package com.example.longkathon.land.service;

import com.example.longkathon.land.dto.LandRequest.CreateLandRequest;
import com.example.longkathon.land.dto.LandResponse.LandDetailsResponse;
import com.example.longkathon.land.dto.LandResponse.LandUserResponse;
import com.example.longkathon.land.entity.Land;
import com.example.longkathon.land.repository.LandRepository;
import com.example.longkathon.landUser.entity.LandUser;
import com.example.longkathon.landUser.repository.LandUserRepository;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LandService {

    private final LandRepository landRepository;
    private final LandUserRepository landUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createLand(CreateLandRequest createLandRequest) {
        User owner = userRepository.findById(createLandRequest.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found with ID: " + createLandRequest.getOwnerId()));

        Land land = Land.builder()
                .landName(createLandRequest.getLandName())
                .ownerId(owner.getUserId())
                .maxMember(createLandRequest.getMaxMember())
                .currentMember(1) // Owner 포함
                .startDate(createLandRequest.getStartDate())
                .endDate(createLandRequest.getEndDate())
                .build();

        Land savedLand = landRepository.save(land);

        LandUser landUser = LandUser.builder()
                .land(savedLand)
                .user(owner)
                .role("owner")
                .build();
        landUserRepository.save(landUser);
    }

    @Transactional
    public void addUserToLand(Long landId, Long userId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        if (land.getCurrentMember() >= land.getMaxMember()) {
            throw new IllegalArgumentException("Land is already full.");
        }

        LandUser landUser = LandUser.builder()
                .land(land)
                .user(user)
                .role("member")
                .build();
        landUserRepository.save(landUser);

        land.setCurrentMember(land.getCurrentMember() + 1);
        landRepository.save(land);
    }

    public LandDetailsResponse getLandDetails(Long landId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        return LandDetailsResponse.builder()
                .landId(land.getLandId())
                .landName(land.getLandName())
                .ownerId(land.getOwnerId())
                .maxMember(land.getMaxMember())
                .currentMember(land.getCurrentMember())
                .urlNameList(land.getUrlNameList())
                .urlList(land.getUrlList())
                .build();
    }

    public List<LandUserResponse> getMembersByLand(Long landId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));

        return land.getLandUsers().stream()
                .map(landUser -> LandUserResponse.builder()
                        .userId(landUser.getUser().getUserId())
                        .name(landUser.getUser().getName())
                        .email(landUser.getUser().getEmail())
                        .role(landUser.getRole())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void addUrlName(Long landId, String urlName) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));
        land.getUrlNameList().add(urlName);
        landRepository.save(land);
    }

    @Transactional
    public void addUrl(Long landId, String url) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found with ID: " + landId));
        land.getUrlList().add(url);
        landRepository.save(land);
    }
}
