package com.sparta.eco.domain;

import com.sparta.eco.post.Dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Setter
@Getter
@Entity(name = "post_table") // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Post { // 생성,수정 시간을 자동으로 만들어줍니다.

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

    @Column
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedAt = LocalDateTime.now();



    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.username = requestDto.getUsername();
        this.fileName = requestDto.getFileName();
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