package com.loginscreen.payload.error;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;

public class Error401  {
  @ApiModelProperty(value = "statusCode", example = "401")
  private int statusCode;
  @Nullable
  @ApiModelProperty (value = "data", notes = "null")
  private Object data;
  @ApiModelProperty (value = "message", example = "Unauthorization")
  private String message;
}
