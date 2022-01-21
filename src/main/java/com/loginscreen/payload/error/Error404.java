package com.loginscreen.payload.error;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Error404 {

  @ApiModelProperty(value = "statusCode", example = "404")
  private int statusCode;
  @Nullable
  @ApiModelProperty(value = "data", example = "null")
  private Object data;
  @ApiModelProperty(value = "message", example = "Not Found,Try again!")
  private String message;
}
