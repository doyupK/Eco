package com.sparta.eco.domain.repository;

import com.sparta.eco.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostidOrderByModifiedAtDesc(Long id);
}