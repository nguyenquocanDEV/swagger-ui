package com.loginscreen.payload.error;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Success201 {

  @ApiModelProperty(value = "statusCode", example = "201")
  private int statusCode;
  @Nullable
  @ApiModelProperty(value = "data", example = "data")
  private Object data;
  @ApiModelProperty(value = "message", example = "Created Success")
  private String message;
}
