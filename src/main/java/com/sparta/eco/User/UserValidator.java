package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;

import java.util.regex.Pattern;


public class UserValidator {

    public static void signupValidate(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        if(!Pattern.matches("^[a-zA-Z0-9]{3,}$", signupRequestDto.getUsername())){
            throw new IllegalArgumentException("아이디는 3자리이상인 영문,숫자로만 입력이 가능랍니다.");
        }

        if(username.contains(password)){
            throw new IllegalArgumentException("아이디에 비밀번호를 포함할 수 없습니다.");
        }

        if(password.length() < 4){
            throw new IllegalArgumentException("비밀번호는 4자리이상 입력해주세요");
        }

        if(password.contains(username)){
            throw new IllegalArgumentException("비밀번호에 아이디를 포함할 수 없습니다.");
        }

        if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", signupRequestDto.getEmail())){
            throw new IllegalArgumentException("이메일 형식으로 입력해 주세요.");
        }

    }
}
