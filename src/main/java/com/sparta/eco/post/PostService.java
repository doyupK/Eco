package com.sparta.eco.post;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.eco.comment.Dto.CommentDetailResponseDto;
import com.sparta.eco.domain.Post;
import com.sparta.eco.domain.User;
import com.sparta.eco.domain.repository.CommentRepository;
import com.sparta.eco.domain.repository.PostRepository;
import com.sparta.eco.domain.repository.UserRepository;
import com.sparta.eco.post.Dto.*;
import com.sparta.eco.responseEntity.Message;
import com.sparta.eco.responseEntity.StatusEnum;
import com.sparta.eco.security.UserDetailsImpl;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Value("${S3Bucket}")
    private String S3Bucket;

    @Autowired
    AmazonS3Client amazonS3Client;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public ResponseEntity<Message> update(Long id, PostRequestDto requestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("???????????? ???????????? ????????????.")
        );
        if(Objects.equals(post.getUsername(), userDetails.getUsername())){
            requestDto.setUsername(userDetails.getUsername());
            requestDto.setFileUrl(post.getFileUrl());
            requestDto.setFileName(post.getFileName());
            post.update(requestDto);
        }
        else throw new IllegalArgumentException("???????????? ????????? ????????? ????????????.");

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("OK");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Message> save(PostRequestDto requestDto, MultipartFile multipartFile, UserDetailsImpl userDetails) {
        if(userDetails==null){ throw new IllegalArgumentException("???????????? ????????? ????????? ?????????.");}
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("???????????? ???????????? ????????????. ????????? ??? ??????????????????. ")
        );

        Message message = new Message();
        if (user != null) {
            FileDataDto fileDataDto = saveImage(multipartFile);

            requestDto.setUsername(userDetails.getUsername());
            requestDto.setFileName(fileDataDto.getImageName());
            requestDto.setFileUrl(fileDataDto.getImagePath());
            Post post = new Post(requestDto);
            postRepository.save(post);
            message.setStatus(StatusEnum.OK);
            message.setMessage("OK");
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Message> getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("???????????? ?????? ?????? ????????????.")
        );
        List<CommentDetailResponseDto> comment = commentRepository.findAllByPostOrderByUpdatedAtDesc(post);


        PostDetailResponseDto postDetailResponseDto = new PostDetailResponseDto();
        postDetailResponseDto.setId(post.getId());
        postDetailResponseDto.setUsername(post.getUsername());
        postDetailResponseDto.setCategory(post.getCategory());
        postDetailResponseDto.setTitle(post.getTitle());
        postDetailResponseDto.setContents(post.getContents());
        postDetailResponseDto.setImgUrl(post.getFileUrl());
        postDetailResponseDto.setComments(comment);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("OK");
        message.setResult(postDetailResponseDto);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Message> getPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<PostResponseDtoMapping> posts = postRepository.findAllByOrderByUpdatedAtDesc();

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("OK");
        message.setResult(posts);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public ResponseEntity<Message> getPostCategory(String category) {
        List<PostResponseDtoMapping> posts = postRepository.findAllByCategoryOrderByUpdatedAtDesc(category);

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("OK");
        message.setResult(posts);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Message> deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("???????????? ???????????? ????????????.")
        );
        if(!Objects.equals(post.getUsername(), userDetails.getUsername())){
            throw new IllegalArgumentException("????????? ????????? ????????????..");
        }
        commentRepository.deleteAllByPost(post);
        postRepository.deleteById(id);


        // S3 ?????? ??????
        amazonS3Client.deleteObject(S3Bucket,post.getFileName());

        Message message = new Message();
        message.setStatus(StatusEnum.OK);
        message.setMessage("?????? ??????");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Transactional
    public FileDataDto saveImage(MultipartFile multipartFile) {
        // ?????? ??????
        String originalName = DateTime.now().toString().replaceAll("[+:]",".")+multipartFile.getOriginalFilename();
        // ?????? ??????
        long size = multipartFile.getSize();

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(multipartFile.getContentType());
        objectMetaData.setContentLength(size);

        // S3??? ?????????
        try {
            amazonS3Client.putObject(
                    new PutObjectRequest(S3Bucket, originalName, multipartFile.getInputStream(), objectMetaData)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
        } catch (IOException e) {
            throw new RuntimeException("?????? ?????? ??????");
        }
        FileDataDto fileDataDto = new FileDataDto();
        // ??????????????? URL ????????????
        String imagePath = amazonS3Client.getUrl(S3Bucket, originalName).toString();
        fileDataDto.setImageName(originalName);
        fileDataDto.setImagePath(imagePath);

//        Message message = new Message();
//        message.setStatus(StatusEnum.OK);
//        message.setMessage("?????? ?????? ??????");
//        message.setData(fileDataDto);
        return fileDataDto;
    }
}