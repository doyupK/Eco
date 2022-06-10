package com.sparta.eco.post.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequestDto {
    private String title;
    private String username;
    private String contents;
}
