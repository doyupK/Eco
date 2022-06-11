package com.sparta.eco.post;

import com.sparta.eco.domain.Post;
import com.sparta.eco.domain.repository.PostRepository;
import com.sparta.eco.post.Dto.PostRequestDto;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.responseEntity.StatusEnum;
import com.sparta.eco.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping("/post/add")
    @ResponseBody
    public ResponseEntity<Message> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.save(requestDto, userDetails);
    }

    @GetMapping("/posts")
    @ResponseBody
    public ResponseEntity<Message> getPosts() {
        return postService.getPosts();
    }

    @PutMapping("/api/posts/{id}")
    @ResponseBody
    public ResponseEntity<Message> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseBody
    public Long deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        if(!Objects.equals(post.getUsername(), userDetails.getUsername())){
            throw new IllegalArgumentException("작성자 정보와 틀립니다..");
        }
        postRepository.deleteById(id);
        return id;
    }
    @GetMapping("/post/detail/{id}")
    public ResponseEntity<Message> detailPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
