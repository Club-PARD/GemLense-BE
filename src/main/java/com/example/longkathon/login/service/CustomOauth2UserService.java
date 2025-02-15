package com.example.longkathon.login.service;


import com.example.longkathon.login.dto.CustomOAuth2User;
import com.example.longkathon.login.dto.GoogleResponse;
import com.example.longkathon.login.dto.OAuth2Response;
import com.example.longkathon.user.dto.UserRequest;
import com.example.longkathon.user.entity.User;
import com.example.longkathon.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOauth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {

            return null;
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if (existData == null) {

            User u = new User();
            u.setUsername(username);
            u.setEmail(oAuth2Response.getEmail());
            u.setName(oAuth2Response.getName());
            u.setRole("ROLE_USER");

            userRepository.save(u);

            UserRequest r = new UserRequest();
            r.setUsername(username);
            r.setName(oAuth2Response.getName());
            r.setRole("ROLE_USER");

            return new CustomOAuth2User(r);
        }
        else {

            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserRequest userDTO = new UserRequest();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());

            return new CustomOAuth2User(userDTO);
        }
    }
}
