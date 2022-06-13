package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.User.Dto.UsernameCheckDto;
import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.UserRepository;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.responseEntity.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Message> registerUser(SignupRequestDto signupRequestDto) {
        Message message = new Message();
        // 회원 ID 중복 확인
        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("중복된 아이디가 포함되어 있습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.findByRealName(signupRequestDto.getRealName()).isPresent()){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("중복된 사용자가 포함되어 있습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Message> validator = UserValidator.signupValidate(signupRequestDto);

        if(validator.getStatusCode().equals(HttpStatus.OK)){
            String password = passwordEncoder.encode(signupRequestDto.getPassword());
            User user = new User(signupRequestDto, password);
            userRepository.save(user);
        }

        return validator;

    }

    //아이디 중복 검사
    public ResponseEntity<Message> usernameCheck(UsernameCheckDto usernameCheckDto) {
        Message message = new Message();
        if(userRepository.findByUsername(usernameCheckDto.getUsername()).isPresent()){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("중복된 아이디가 존재합니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}