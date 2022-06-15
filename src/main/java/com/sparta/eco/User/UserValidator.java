package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.responseEntity.StatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.regex.Pattern;


public class UserValidator {

    public static ResponseEntity<Message> signupValidate(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        Message message = new Message();

        if(!Pattern.matches("^[a-zA-Z0-9]{3,}$", signupRequestDto.getUsername())){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("아이디는 3자리이상인 영문,숫자로만 입력이 가능랍니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(username.contains(password)){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("아이디에 비밀번호를 포함할 수 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(!Pattern.matches("^[a-zA-Z0-9]{6,}$", signupRequestDto.getPassword())){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("비밀번호는 6자리이상인 영문,숫자로만 입력이 가능랍니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(!signupRequestDto.getPassword().equals(signupRequestDto.getPasswordCheck())){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(password.contains(username)){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("비밀번호에 아이디를 포함할 수 없습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", signupRequestDto.getEmail())){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("이메일 형식으로 입력해 주세요.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        message.setStatus(StatusEnum.OK);
        message.setMessage("회원가입 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
