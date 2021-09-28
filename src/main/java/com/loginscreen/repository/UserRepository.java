package com.loginscreen.repository;

import com.loginscreen.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findByUsername(String username);
}