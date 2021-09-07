package com.loginscreen.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

  @RequestMapping("/oauth2/login/succes")
  public String success(OAuth2AuthenticationToken oauth2) {
    return "forward:/auth/login/success";


  }
}
