package com.codeworld.motorsportapp.repository;

import com.codeworld.motorsportapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
