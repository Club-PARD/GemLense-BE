package com.example.longkathon;

import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j //로그에다가 프린트할때 쓰는 어노테이션
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        log.info("google에서 받아온 UserRequest : " + oAuth2UserRequest);
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        log.info("oauth에서 받아온 정보 : " + oAuth2User.getAttributes());


        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            User u = new User(null, name, email, null, null, null);
            userRepository.save(u);
        }
        return super.loadUser(oAuth2UserRequest);
    }
}