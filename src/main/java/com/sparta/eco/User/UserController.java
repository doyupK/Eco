package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.User.Dto.UsernameCheckDto;
import com.sparta.eco.domain.User;
import com.sparta.eco.responseEntity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public ResponseEntity<Message> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.registerUser(signupRequestDto);
    }

    @PostMapping("/user/signup/check")
    public ResponseEntity<Message> usernameCheck(@RequestBody UsernameCheckDto usernameCheckDto){
        return userService.usernameCheck(usernameCheckDto);
    }
}