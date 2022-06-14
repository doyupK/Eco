package com.sparta.eco.post;

import com.sparta.eco.post.Dto.PostRequestDto;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;




@RestController
public class PostController {

    private final PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "test hello", description = "hello api example")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })


    @GetMapping("/posts")
    public ResponseEntity<Message> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPosts(userDetails);
    }

    @GetMapping("/posts/{category}/{id}")
    public ResponseEntity<Message> detailPost(@PathVariable String category, @PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    @GetMapping("/posts/{category}")
    public ResponseEntity<Message> detailPost(@PathVariable String category) {
        return postService.getPostCategory(category);
    }
//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/posts/add", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Message> createPost(@RequestPart PostRequestDto requestDto, @RequestPart MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.save(requestDto, multipartFile , userDetails);
    }

    @PutMapping("/posts/{category}/{id}")
    public ResponseEntity<Message> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/posts/{category}/{id}")
    public ResponseEntity<Message> deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails);
    }

//    @PostMapping("/posts/upload")
//    public ResponseEntity<Message> upload(MultipartFile multipartFile) {
//        return postService.saveImage(multipartFile);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
