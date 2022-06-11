package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;


public class UserValidator {

    public static void signupValidate(SignupRequestDto signupRequestDto) {

        String password = signupRequestDto.getPassword();

        if(password == null || password.length() < 8 || password.length() > 10){
            throw new IllegalArgumentException("비밀번호는 8 ~ 10자리로 입력해주세요");
        }

    }
}
