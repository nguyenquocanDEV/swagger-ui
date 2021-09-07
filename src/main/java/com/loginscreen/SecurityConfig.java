package com.loginscreen;


import com.loginscreen.model.User;
import com.loginscreen.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfiguration {

  BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
  @Autowired
  LoginServices dao;

  // quản lý dữ liệu người dùng
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(username -> {
      try {
        User user = dao.findById(username);
        String password = user.getPassword();
        return org.springframework.security.core.userdetails.User.withUsername(username)
            .password(password).build();
      } catch (Exception e) {
        throw new UsernameNotFoundException(username + "not found");
      }
    });
  }


  protected void configure(final HttpSecurity http) throws Exception {
    http
        .csrf().disable().cors().disable();
    http
        .authorizeRequests()
        .anyRequest().permitAll();
    http
        .formLogin()
        .loginPage("/admin/login/form")
        .loginProcessingUrl("/admin/login")
        .defaultSuccessUrl("/admin/login/success", true)
        .failureUrl("/admin/login/error");

    // ...
  }

  //mã hoá pass
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
