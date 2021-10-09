package com.loginscreen.services.impl;

import com.loginscreen.model.UserEntity;
import com.loginscreen.repository.UserRepository;
import com.loginscreen.services.UserServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices {

  @Autowired
  UserRepository dao;

  @Override
  public List<UserEntity> findAll() {
    return dao.findAll();
  }
}
