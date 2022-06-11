package com.sparta.eco.User;

import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User registerUser(SignupRequestDto signupRequestDto) {

        // 회원 ID 중복 확인
        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다. ");
        }

        if(userRepository.findByRealname(signupRequestDto.getRealname()).isPresent()){
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(signupRequestDto, password);
        return userRepository.save(user);

    }

    //아이디 중복 검사
    public boolean usernameCheck(String username) {
        return userRepository.existsByUsername(username);
    }
}