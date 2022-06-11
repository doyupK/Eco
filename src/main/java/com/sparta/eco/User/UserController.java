package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public User registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.registerUser(signupRequestDto);

    }

    @PostMapping("/user/signup/check")
    public boolean usernameCheck(@RequestBody SignupRequestDto signupRequestDto){
        return userService.usernameCheck(signupRequestDto.getUsername());
    }



}