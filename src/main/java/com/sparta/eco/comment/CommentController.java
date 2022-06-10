package com.sparta.eco.comment;

import com.sparta.eco.domain.Comment;
import com.sparta.eco.domain.repository.CommentRepository;
import com.sparta.eco.comment.Dto.CommentRequestDto;
import com.sparta.eco.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
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
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @GetMapping("/api/posts/comments/{id}")
    @ResponseBody
    public List<Comment> getComments(@PathVariable Long id) {

        return commentService.getComments(id);
    }

    @PostMapping("/api/comments/{id}")
    @ResponseBody
    public Long createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if(userDetails==null){
            throw new IllegalArgumentException("로그인 정보 없음");
        }
        if(requestDto.getContents().equals("")){
            throw new IllegalArgumentException("댓글 내용 없음");
        }
        commentService.save(id, requestDto, userDetails);
        return id;
    }



    @PutMapping("/api/posts/comment/{id}")
    @ResponseBody
    public void updateMemo(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails.getUsername());
        commentService.update(id, requestDto, userDetails);
    }

    @DeleteMapping("/api/posts/comment/{id}")
    @ResponseBody
    public Long deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        if(!Objects.equals(comment.getUsername(), userDetails.getUsername())){
            throw new IllegalArgumentException("작성자 정보와 틀립니다..");
        }
        commentRepository.deleteById(id);
        return id;
    }



    @GetMapping("/api/detail/{id}/comment")
    public String detailPost(Model model, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("댓글이 존재 하지 않습니다.")
        );

        model.addAttribute("comment", comment);
        return "detail";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


}
