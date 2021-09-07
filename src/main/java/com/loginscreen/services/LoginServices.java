package com.loginscreen.services;

import com.loginscreen.model.User;
import java.util.List;

public interface LoginServices {

  public List<User> findAll();

  public User findById(String username);

}
