package com.sparta.eco.comment;


import com.sparta.eco.comment.Dto.CommentRequestDto;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Operation(summary = "test hello", description = "hello api example")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })


    @PostMapping("/posts/{id}/comment")
    public ResponseEntity<Message> createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        if(userDetails==null){
            throw new IllegalArgumentException("로그인 정보 없음");
        }
        if(requestDto.getContents().equals("")){
            throw new IllegalArgumentException("댓글 내용 없음");
        }

        return commentService.save(id, requestDto, userDetails);
    }

    @PutMapping("/posts/{id}/comments/{commentid}")
    public ResponseEntity<Message> updateMemo(@PathVariable Long id,@PathVariable Long commentid, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.update(id, requestDto, userDetails, commentid);
    }
    @DeleteMapping("/posts/{id}/comments/{commentid}")
    public ResponseEntity<Message> deleteMemo(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentid) {
        return commentService.deleteComment(id,commentid, userDetails);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


}
