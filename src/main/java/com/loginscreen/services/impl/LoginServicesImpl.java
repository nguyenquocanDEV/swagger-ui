package com.loginscreen.services.impl;

import com.loginscreen.model.User;
import com.loginscreen.repository.UserRepository;
import com.loginscreen.services.LoginServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServicesImpl implements LoginServices {

  @Autowired
  UserRepository user;


  @Override
  public List<User> findAll() {
    return user.findAll();
  }


  @Override
  public User findById(String username) {
    return user.findById(username).get();
  }
}
