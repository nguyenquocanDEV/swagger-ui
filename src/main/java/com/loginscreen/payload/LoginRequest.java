package com.loginscreen.payload;

import lombok.Data;

@Data
public class LoginRequest {

  private String username;
  private String password;
}
