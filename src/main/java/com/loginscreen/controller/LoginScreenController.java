package com.loginscreen.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginScreenController {

  @RequestMapping("/admin/login/form")
  public String loginForm(Model model) {

    model.addAttribute("message", "Please login");
    return "views/login";
  }

  @RequestMapping("/admin/login/success")
  public String loginSuccess(Model model) {

    return "redirect:/views/ok";
  }

  @RequestMapping("/admin/login/error")
  public String loginError(Model model) {
    model.addAttribute("message", "Error");
    return "views/login";
  }

  @GetMapping("/oauth2/login/success")
  public String success(OAuth2AuthenticationToken oauth) {
    return "redirect:/views/ok";
  }

  @GetMapping("/oauth2/login/error")
  public String error() {
    return "views/login";
  }
}


