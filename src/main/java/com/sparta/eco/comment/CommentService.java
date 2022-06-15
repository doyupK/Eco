package com.sparta.eco.comment;

import com.sparta.eco.domain.Comment;
import com.sparta.eco.domain.Post;
import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.CommentRepository;
import com.sparta.eco.domain.repository.PostRepository;
import com.sparta.eco.domain.repository.UserRepository;
import com.sparta.eco.comment.Dto.CommentRequestDto;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.responseEntity.StatusEnum;
import com.sparta.eco.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public ResponseEntity<Message> save(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다. 로그인 후 시도해주세요. ")
        );
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시글 오류"));
        if (user != null) {
            requestDto.setPost(post);
            requestDto.setUsername(userDetails.getUsername());
            Comment comment = new Comment(requestDto);
            commentRepository.save(comment);
        }
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("댓글 등록 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Message> update(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails, Long commentid) {

        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(Objects.equals(comment.getUsername(), userDetails.getUsername())){

            comment.update(requestDto);
        }
        else throw new IllegalArgumentException("작성자와 로그인 정보가 다릅니다.");

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("댓글 수정 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    public ResponseEntity<Message> deleteComment(Long id, Long commentid, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("게시글 오류"));
        Comment comment = commentRepository.findByPostAndId(post,commentid);
        if(!Objects.equals(comment.getUsername(), userDetails.getUsername())){
            throw new IllegalArgumentException("작성자 정보와 틀립니다..");
        }
        commentRepository.deleteById(commentid);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("댓글 삭제 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
