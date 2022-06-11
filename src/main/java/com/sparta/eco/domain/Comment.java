package com.sparta.eco.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.eco.comment.Dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "comment_table")
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    public Comment(CommentRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.post = requestDto.getPost();
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
