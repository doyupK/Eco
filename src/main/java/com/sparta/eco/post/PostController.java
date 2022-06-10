package com.sparta.eco.post;

import com.sparta.eco.domain.Post;
import com.sparta.eco.domain.repository.PostRepository;
import com.sparta.eco.post.Dto.PostRequestDto;
import com.sparta.eco.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    @Autowired
    public PostController(PostService postService, PostRepository postRepository) {
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @PostMapping("/posts")
    @ResponseBody
    public boolean createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

//        postRepository.save(post);
        postService.save(requestDto, userDetails);
        return true;

    }

    @GetMapping("/posts")
    @ResponseBody
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @PutMapping("/api/posts/{id}")
    @ResponseBody
    public Long updateMemo(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getUsername());
        postService.update(id, requestDto, userDetails);
//        return "작성자와 로그인 정보가 다릅니다.";
        return id;
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



    @GetMapping("/api/detail/{id}")
    public String detailPost(Model model, @PathVariable Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재 하지 않습니다.")
        );

        model.addAttribute("post", post);
        return "detail";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


}
