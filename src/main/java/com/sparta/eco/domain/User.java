package com.sparta.eco.domain;

import com.sparta.eco.User.Dto.SignupRequestDto;
import com.sparta.eco.User.UserValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "User_table")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String realname;

    @Column(nullable = false)
    private String email;

    public User(SignupRequestDto signupRequestDto, String password) {

        //유효성검사
        UserValidator.signupValidate(signupRequestDto);

        this.username = signupRequestDto.getUsername();
        this.password = password;
        this.realname = signupRequestDto.getRealname();
        this.email = signupRequestDto.getEmail();

    }
}