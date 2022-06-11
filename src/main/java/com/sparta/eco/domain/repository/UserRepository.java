package com.sparta.eco.domain.repository;

import com.sparta.eco.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByRealName(String realname);

    boolean existsByUsername(String username);

}
