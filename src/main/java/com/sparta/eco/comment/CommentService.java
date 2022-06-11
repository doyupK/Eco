package com.sparta.eco.comment;

import com.sparta.eco.comment.Dto.CommentDetailResponseDto;
import com.sparta.eco.domain.Comment;
import com.sparta.eco.domain.Post;
import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.CommentRepository;
import com.sparta.eco.domain.repository.PostRepository;
import com.sparta.eco.domain.repository.UserRepository;
import com.sparta.eco.comment.Dto.CommentRequestDto;
import com.sparta.eco.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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

    public void save(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
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
    }

    @Transactional
    public void update(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(Objects.equals(comment.getUsername(), userDetails.getUsername())){
            comment.update(requestDto);
        }
        else throw new IllegalArgumentException("작성자와 로그인 정보가 다릅니다.");
    }

    public List<CommentDetailResponseDto> getComments(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시글 없음 오류"));

        return commentRepository.findAllByPostOrderByModifiedAtDesc(post);
    }
}
