package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.UserRepository;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.responseEntity.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Message> registerUser(SignupRequestDto requestDto) {
        String realname = requestDto.getRealname();
        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        Message message = new Message();
        if(realname.contains(requestDto.getPassword())){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("비밀번호에 아이디가 포함되어 있습니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if(!Pattern.matches("^[a-zA-Z\\d]*$",username) || username.length()<=3){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("닉네임에 불가한 문자가 있거나, 최소 3자 이상이어야 합니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if(requestDto.getPassword().length()<=4 || requestDto.getPassword().contains(username)){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage("비밀번호에 닉네임이 포함되었거나, 최소 4자 이상이어야 합니다.");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        else{
            Optional<User> found = userRepository.findByUsername(username);
            if (found.isPresent()) {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage("중복된 사용자 ID입니다.");
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            // 패스워드 암호화
            String password = passwordEncoder.encode(requestDto.getPassword());

            User user = new User(username, realname, password, email);
            userRepository.save(user);
            message.setStatus(StatusEnum.OK);
            message.setMessage("회원가입 성공");
            return new ResponseEntity<>(message, HttpStatus.OK);

        }
    }


}