package com.sparta.eco.User.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String realName;
    private String password;
    private String passwordCheck;
    private String email;
}