package com.main.spring.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.spring.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByusername(String username);
}
