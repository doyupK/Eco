package com.sparta.eco.domain.repository;

import com.sparta.eco.comment.Dto.CommentDetailResponseDto;
import com.sparta.eco.domain.Comment;
import com.sparta.eco.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<CommentDetailResponseDto> findAllByPostOrderByUpdatedAtDesc(Post post);

    Comment findByPostAndId(Post post, Long id);

    void deleteAllByPost(Post Post);
}