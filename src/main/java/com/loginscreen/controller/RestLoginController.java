package com.loginscreen.controller;

import com.loginscreen.jwt.JwtTokenProvider;

import com.loginscreen.payload.AboutResponse;
import com.loginscreen.payload.LoginRequest;
import com.loginscreen.payload.LoginResponse;
import com.loginscreen.repository.UserRepository;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
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
  UserRepository dao;

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


  @GetMapping("/about")
  public AboutResponse about(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    // Kiểm tra xem header Authorization có chứa thông tin jwt không
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      String username = tokenProvider.getUsernameFromToken(bearerToken.substring(7));
      return new AboutResponse(username);
    }
    return new AboutResponse("Error");
  }

  @GetMapping("/login")
  public String test() {
    return new String("test get login" + dao.findAll());
  }
}
