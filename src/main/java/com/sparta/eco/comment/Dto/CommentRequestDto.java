package com.sparta.eco.comment.Dto;

import com.sparta.eco.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
    private Post post;
    private String username;
    private String contents;
}
