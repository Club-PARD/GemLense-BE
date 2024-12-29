//package com.example.longkathon;
//
//import com.example.longkathon.user.entity.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Map;
//
//public class CustomOAuth2User implements OAuth2User {
//
//    private final Map<String, Object> attributes;
//    private final User user;
//
//    public CustomOAuth2User(Map<String, Object> attributes, User user) {
//        this.attributes = attributes;
//        this.user = user;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return attributes;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.emptyList(); // 권한이 필요하면 추가
//    }
//
//    @Override
//    public String getName() {
//        return user.getName();
//    }
//
//    public String getEmail() {
//        return user.getEmail();
//    }
//
//    public Long getId() {
//        return user.getUserId();
//    }
//
//    public User getUser() {
//        return user;
//    }
//}
