package com.sparta.eco.user;

import com.sparta.eco.security.UserDetailsImpl;
import com.sparta.eco.user.Dto.SignupRequestDto;
import com.sparta.eco.user.Dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/loginView")
    public String login() {
        return "login";
    }


    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public ModelAndView signup() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signup");
        return mav;
    }

    // 게시글 작성
    @GetMapping("/write")
    public String write() {
        return "write" ;
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    @Transactional
    public ModelAndView registerUser(SignupRequestDto requestDto) {
        ModelAndView mav = new ModelAndView();

        return userService.registerUser(requestDto);
    }

    // 회원 관련 정보 받기
    @PostMapping("/user/userinfo")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        return new UserInfoDto(username);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
