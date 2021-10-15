package com.loginscreen.controller.rest;

import com.loginscreen.jwt.JwtAuthenticationFilter;
import com.loginscreen.jwt.JwtTokenProvider;
import com.loginscreen.payload.AboutInfo;
import com.loginscreen.payload.AboutResponse;
import com.loginscreen.payload.BaseResponse;
import com.loginscreen.payload.LoginInfo;
import com.loginscreen.payload.LoginRequest;
import com.loginscreen.payload.error.Error401;
import com.loginscreen.payload.error.Error403;
import com.loginscreen.payload.error.Error404;
import com.loginscreen.payload.error.Success201;
import com.loginscreen.services.UserServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestLoginController {

  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenProvider tokenProvider;
  @Autowired
  private JwtAuthenticationFilter filter;
  @Autowired
  private UserServices userServices;

  @PostMapping("/login")
  @ApiOperation(value = "login")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = BaseResponse.class),
      @ApiResponse(code = 201, message = "Create", response = Success201.class),
      @ApiResponse(code = 403, message = "Forbidden", response = Error403.class),
      @ApiResponse(code = 404, message = "Not Found", response = Error404.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = Error401.class)
  })
  public BaseResponse authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
    try {
      // Xác thực thông tin người dùng Request lên
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(),
              loginRequest.getPassword()
          )
      );
      // Set thông tin authentication vào Security Context
      SecurityContextHolder.getContext().setAuthentication(authentication);
      // Trả về jwt cho người dùng.
      String jwt = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());
      LoginInfo loginInfo = new LoginInfo(jwt);
      return BaseResponse.success(loginInfo);
    } catch (AuthenticationException e) {
      if (e instanceof BadCredentialsException) {
        return BaseResponse.failure(HttpStatus.UNAUTHORIZED.value());
      }
      return BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  @GetMapping("/about")
  @ApiOperation(value = "about")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = AboutResponse.class),
      @ApiResponse(code = 403, message = "Forbidden", response = Error403.class),
      @ApiResponse(code = 404, message = "Not Found", response = Error404.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = Error401.class)
  })
  public AboutResponse about(HttpServletRequest request, HttpServletResponse response) {
    try {
      String bearerToken = request.getHeader("Authorization");
      // Kiểm tra xem header Authorization có chứa thông tin jwt không
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        String username = tokenProvider.getUsernameFromToken(bearerToken.substring(7));
        AboutInfo aboutInfo = new AboutInfo(username);
        return AboutResponse.success(aboutInfo);
      }
      return AboutResponse.failure(HttpStatus.BAD_REQUEST.value());
    } catch (Exception e) {
      if (e instanceof BadCredentialsException) {
        return AboutResponse.failure(HttpStatus.UNAUTHORIZED.value());
      }
      return AboutResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }
}
