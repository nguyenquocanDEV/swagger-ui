package com.loginscreen.payload.error;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;

public class Error403 {
  @ApiModelProperty(value = "statusCode", example = "403")
  private int statusCode;
  @Nullable
  @ApiModelProperty (value = "data", notes = "null")
  private Object data;
  @ApiModelProperty (value = "message", example = "Forbidden,Try again!")
  private String message;
}
