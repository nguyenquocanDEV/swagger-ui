package com.loginscreen.payload;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

  @ApiModelProperty(value = "statusCode", example = "200")
  private int statusCode;
  @Nullable
  @ApiModelProperty (value = "data")
  private LoginInfo data;
  @ApiModelProperty (value = "message", example = "Successful")
  private String message;

  public static BaseResponse success(Object data) {
    return new BaseResponse(HttpStatus.OK.value(), (LoginInfo) data, null);
  }

  public static BaseResponse failure(int statusCode) {
    switch (statusCode) {
      case 400:
        return new BaseResponse(HttpStatus.BAD_REQUEST.value(), null,
            HttpStatus.BAD_REQUEST.getReasonPhrase());
      case 401:
        return new BaseResponse(HttpStatus.UNAUTHORIZED.value(), null,
            HttpStatus.UNAUTHORIZED.getReasonPhrase());
      default:
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
  }
}
