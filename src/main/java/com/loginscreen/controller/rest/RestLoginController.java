package com.loginscreen.controller.rest;

import com.loginscreen.jwt.JwtAuthenticationFilter;
import com.loginscreen.jwt.JwtTokenProvider;
import com.loginscreen.model.UserEntity;
import com.loginscreen.payload.AboutResponse;
import com.loginscreen.payload.LoginRequest;
import com.loginscreen.payload.LoginResponse;
import com.loginscreen.payload.ResponseTemplate;
import com.loginscreen.services.UserServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
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
      @ApiResponse(code = 200, message = "OK", response = ResponseTemplate.class),
      @ApiResponse(code = 201, message = "Create", response = ResponseTemplate.class),
      @ApiResponse(code = 403, message = "Forbidden", response = ResponseTemplate.class),
      @ApiResponse(code = 404, message = "Not Found", response = ResponseTemplate.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = ResponseTemplate.class)
  })
  public ResponseTemplate authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {
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
      LoginResponse loginResponse = new LoginResponse(jwt);
      return ResponseTemplate.success(loginResponse);
    } catch (AuthenticationException e) {
      if (e instanceof BadCredentialsException) {
        return ResponseTemplate.failure(HttpStatus.UNAUTHORIZED.value());
      }
      return ResponseTemplate.failure(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  @GetMapping("/about")
  @ApiOperation(value = "about")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = ResponseTemplate.class),
      @ApiResponse(code = 403, message = "Forbidden", response = ResponseTemplate.class),
      @ApiResponse(code = 404, message = "Forbidden", response = ResponseTemplate.class),
      @ApiResponse(code = 401, message = "Unauthorized", response = ResponseTemplate.class)
  })
  public ResponseTemplate about(HttpServletRequest request, HttpServletResponse response) {
    try {
      String bearerToken = request.getHeader("Authorization");
      // Kiểm tra xem header Authorization có chứa thông tin jwt không
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        String username = tokenProvider.getUsernameFromToken(bearerToken.substring(7));
        AboutResponse aboutResponse = new AboutResponse(username);
        return ResponseTemplate.success(aboutResponse);
      }
      return ResponseTemplate.failure(HttpStatus.BAD_REQUEST.value());
    } catch (Exception e) {
      if (e instanceof BadCredentialsException) {
        return ResponseTemplate.failure(HttpStatus.UNAUTHORIZED.value());
      }
      return ResponseTemplate.failure(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  @GetMapping("/login")
  public List<UserEntity> test(){
    return userServices.findAll();
  }
}
