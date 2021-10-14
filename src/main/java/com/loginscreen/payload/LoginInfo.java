package com.loginscreen.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {
  @ApiModelProperty(value = "accessToken", example = "ad123891237hjsdnkadsgsgsdgsdgưet24523")
  private String accessToken;
}
