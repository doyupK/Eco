package com.sparta.eco.User;

import com.sparta.eco.User.Dto.LoginRequestDto;
import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.responseEntity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;
//    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService) { //맴버변수
        this.userService = userService;

    }

    // 회원 로그인 페이지
//    @GetMapping("/user/login")
//    public ResponseEntity<Message> login(@RequestBody LoginRequestDto requestDto) {
//        return userService.loginUser(requestDto);
//    }

    // 회원 가입 페이지
//    @GetMapping("/user/signup")
//    public String signup() {
//        return "signup";
//    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public ResponseEntity<Message> registerUser(@RequestBody SignupRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }



    // kakao
//    @GetMapping("/user/kakao/callback")
//    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//// authorizedCode: 카카오 서버로부터 받은 인가 코드
//        kakaoUserService.kakaoLogin(code);
//
//        return "redirect:/";
//    }
}