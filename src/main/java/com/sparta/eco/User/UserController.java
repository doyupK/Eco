package com.sparta.eco.User;

<<<<<<< HEAD

import com.sparta.eco.User.Dto.SignupRequestDto;
=======
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.User.UserService;
>>>>>>> origin/master
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private final UserService userService;
<<<<<<< HEAD

=======
//    private final KakaoUserService kakaoUserService;
>>>>>>> origin/master

    @Autowired
    public UserController(UserService userService) { //맴버변수
        this.userService = userService;
<<<<<<< HEAD
=======

>>>>>>> origin/master
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "redirect:/user/login";
    }
<<<<<<< HEAD
=======



    // kakao
//    @GetMapping("/user/kakao/callback")
//    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//// authorizedCode: 카카오 서버로부터 받은 인가 코드
//        kakaoUserService.kakaoLogin(code);
//
//        return "redirect:/";
//    }
>>>>>>> origin/master
}