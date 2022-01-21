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
public class AboutResponse {

  @ApiModelProperty(value = "statusCode", example = "200")
  private int statusCode;
  @Nullable
  @ApiModelProperty(value = "data")
  private AboutInfo data;
  @ApiModelProperty(value = "message", example = "Successful")
  private String message;

  public static AboutResponse success(Object data) {
    return new AboutResponse(HttpStatus.OK.value(), (AboutInfo) data, null);
  }

  public static AboutResponse failure(int statusCode) {
    switch (statusCode) {
      case 400:
        return new AboutResponse(HttpStatus.BAD_REQUEST.value(), null,
            HttpStatus.BAD_REQUEST.getReasonPhrase());
      case 401:
        return new AboutResponse(HttpStatus.UNAUTHORIZED.value(), null,
            HttpStatus.UNAUTHORIZED.getReasonPhrase());
      default:
        return new AboutResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
  }
}
