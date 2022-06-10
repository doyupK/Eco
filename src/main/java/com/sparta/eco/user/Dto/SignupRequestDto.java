package com.sparta.eco.user.Dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SignupRequestDto {

    private String username;
    private String password;
    private String passwordCheck;

    public SignupRequestDto(String username, String password, String passwordCheck) {
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
    }
}