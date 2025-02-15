
package com.example.longkathon.user.dto;

import lombok.*;

@Getter
@Setter
public class UserRequest {
    private String role;
    private String name;
    private String username;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserNameEmailRequest {
        private String name;
        private String email;
    }
}
