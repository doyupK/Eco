package com.sparta.eco.post;

import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.PostRepository;
import com.sparta.eco.post.Dto.PostRequestDto;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class PostController {

    private final PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<Message> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPosts(userDetails);
    }

    @GetMapping("/post/{category}/{id}")
    public ResponseEntity<Message> detailPost(@PathVariable String category, @PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    @GetMapping("/post/{category}")
    public ResponseEntity<Message> detailPost(@PathVariable String category) {
        return postService.getPostCategory(category);
    }

    @PostMapping("/post/add")
    public ResponseEntity<Message> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.save(requestDto, userDetails);
    }

    @PutMapping("/post/{category}/{id}")
    public ResponseEntity<Message> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/post/{category}/{id}")
    public ResponseEntity<Message> deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails);
    }

    @PostMapping("/upload")
    public ResponseEntity<Message> upload(MultipartFile multipartFile) {
        return postService.saveImage(multipartFile);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
