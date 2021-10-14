package com.loginscreen.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutResponse {
  @ApiModelProperty (value = "username", example = "quocan123")
  private String username;
}
