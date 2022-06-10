package com.sparta.eco.domain;

import com.sparta.eco.comment.Dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long postid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    public Comment(CommentRequestDto requestDto) {
        this.postid = requestDto.getPostid();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();

    }
}
