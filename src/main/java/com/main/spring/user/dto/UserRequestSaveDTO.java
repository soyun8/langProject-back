package com.main.spring.user.dto;

import java.time.LocalDateTime;

import com.main.spring.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestSaveDTO{
    private String username;
    private String password;
    private String email;
    private String name;
    private int age;
    private String provider;
    private String providerId;
    private String role;
    private LocalDateTime createDate;

    @Builder
    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .age(age)
                .provider(provider)
                .providerId(providerId)
                .role("ROLE_USER")
                .createDate(LocalDateTime.now())
                .build();
    }

}
