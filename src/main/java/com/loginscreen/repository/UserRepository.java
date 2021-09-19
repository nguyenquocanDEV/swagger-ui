package com.loginscreen.repository;

import com.loginscreen.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  User findByUsername(String username);
}