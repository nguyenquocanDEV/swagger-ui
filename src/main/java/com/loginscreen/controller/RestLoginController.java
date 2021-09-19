package com.loginscreen.controller;

import com.loginscreen.jwt.JwtTokenProvider;

import com.loginscreen.payload.LoginRequest;
import com.loginscreen.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
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


  @PostMapping("/login")
  public LoginResponse authenticateUser(@RequestBody LoginRequest loginRequest) {

    // Xác thực thông tin người dùng Request lên
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        )
    );

    // Nếu không xảy ra exception tức là thông tin hợp lệ
    // Set thông tin authentication vào Security Context
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Trả về jwt cho người dùng.
    String jwt = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());
    return new LoginResponse(jwt);
  }

  // Api /api/random yêu cầu phải xác thực mới có thể request
  @GetMapping("/about")
  public String about() {
    return new String("JWT Hợp lệ mới có thể thấy được message này");
  }

  @GetMapping("/login")
  public String test() {
    return new String("test get login");
  }
}
