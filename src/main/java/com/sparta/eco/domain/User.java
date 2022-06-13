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
    private String realName;

    @Column(nullable = false)
    private String email;

    public User(SignupRequestDto signupRequestDto, String password) {

        this.username = signupRequestDto.getUsername();
        this.password = password;
        this.realName = signupRequestDto.getRealName();
        this.email = signupRequestDto.getEmail();

    }
}