package com.sparta.eco.comment.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
    private Long postid;
    private String username;
    private String contents;
}
