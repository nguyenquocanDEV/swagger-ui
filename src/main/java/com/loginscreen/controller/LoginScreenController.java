package com.loginscreen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginScreenController {
  @RequestMapping("/")
  public String fb() {

    return "views/index";
  }

  @RequestMapping("/admin/login/form")
  public String loginForm() {

    return "views/login";
  }

  @RequestMapping("/admin/login/error")
  public String loginError() {

    return "views/loi";
  }

  @RequestMapping("/admin/login/success")
  public String loginSuccess() {

    return "views/ok";
  }


  @GetMapping("/oauth2/login/error")
  public String error() {
    return "views/loi";
  }
}


