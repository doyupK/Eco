package com.sparta.eco.post;

import com.sparta.eco.domain.Post;
import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.PostRepository;
import com.sparta.eco.domain.repository.UserRepository;
import com.sparta.eco.post.Dto.PostRequestDto;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.responseEntity.StatusEnum;
import com.sparta.eco.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Message> update(Long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if(Objects.equals(post.getUsername(), userDetails.getUsername())){
            post.update(requestDto);
        }
        else throw new IllegalArgumentException("작성자와 로그인 정보가 다릅니다.");

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("OK");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



    public ResponseEntity<Message> save(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다. 로그인 후 시도해주세요. ")
        );
        Message message = new Message();
        if (user != null) {
            requestDto.setUsername(userDetails.getUsername());
            Post post = new Post(requestDto);
            postRepository.save(post);
            message.setStatus(StatusEnum.OK);
            message.setMessage("OK");
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Message> getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재 하지 않습니다.")
        );

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("OK");
        message.setData(post);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Message> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("OK");
        message.setData(posts);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}