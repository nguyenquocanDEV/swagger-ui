package com.loginscreen.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequest {

  @ApiModelProperty(value = "username", example = "quocan123")
  private String username;
  @ApiModelProperty (value = "password", example = "*********")
  private String password;
}
