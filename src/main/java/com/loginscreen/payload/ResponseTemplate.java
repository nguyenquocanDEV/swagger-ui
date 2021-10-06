package com.loginscreen.payload;

import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate {

  private int statusCode;
  @Nullable
  private Object data;
  private String message;

  public static ResponseTemplate success(Object data) {
    return new ResponseTemplate(HttpStatus.OK.value(), data, null);
  }

  public static ResponseTemplate failure(int statusCode) {
    switch (statusCode) {
      case 400:
        return new ResponseTemplate(HttpStatus.BAD_REQUEST.value(), null,
            HttpStatus.BAD_REQUEST.getReasonPhrase());
      case 401:
        return new ResponseTemplate(HttpStatus.UNAUTHORIZED.value(), null,
            HttpStatus.UNAUTHORIZED.getReasonPhrase());
      default:
        return new ResponseTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
  }
}
