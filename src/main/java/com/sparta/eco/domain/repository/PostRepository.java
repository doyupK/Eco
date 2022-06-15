package com.sparta.eco.domain.repository;

import com.sparta.eco.domain.Post;
import com.sparta.eco.post.Dto.PostDetailResponseDtoMapping;
import com.sparta.eco.post.Dto.PostResponseDtoMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<PostResponseDtoMapping> findAllByOrderByUpdatedAtDesc();

    List<PostResponseDtoMapping> findAllByCategoryOrderByUpdatedAtDesc(String category);

}