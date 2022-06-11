package com.sparta.eco.domain;

import com.sparta.eco.post.Dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Setter
@Getter
@Entity(name = "post_table") // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Post extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String fileUrl;



    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.fileUrl = requestDto.getFileUrl();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.fileUrl = requestDto.getFileUrl();
    }


}