package com.loginscreen.services;

import com.loginscreen.model.UserEntity;
import java.util.List;

public interface UserServices {

  public List<UserEntity> findAll();
}
