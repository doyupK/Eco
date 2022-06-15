package com.sparta.eco.post.Dto;

import com.sparta.eco.comment.Dto.CommentDetailResponseDto;
import com.sparta.eco.domain.Comment;
import lombok.Data;

import java.util.List;

@Data
public class PostDetailResponseDto {
    Long id;
    String title;
    String category;
    String username;
    String contents;
    String imgUrl;
    List<CommentDetailResponseDto> comments;
}