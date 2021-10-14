package com.loginscreen.payload.error;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;

public class Error404 {
  @ApiModelProperty(value = "statusCode", example = "404")
  private int statusCode;
  @Nullable
  @ApiModelProperty (value = "data", notes = "null")
  private Object data;
  @ApiModelProperty (value = "message", example = "Not Found,Try again!")
  private String message;
}
