package com.loginscreen;


import com.loginscreen.model.User;
import com.loginscreen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

  @Autowired
  UserRepository dao;


  //check
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
//
    auth.userDetailsService(username -> {
      try {
        User user = dao.findByUsername(username);
        String password = pe.encode(user.getPassword());

        return org.springframework.security.core.userdetails.User.withUsername(username)
            .password(password).roles("").build();
      } catch (Exception e) {
        throw new UsernameNotFoundException(username + "not found!");
      }
    });

  }

  //phân quyền
  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    http
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/css/**", "/", "/login**", "/login", "/webjars/**", "/error**")
        .permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/admin/login/form")
        .loginProcessingUrl("/j_spring_security_check") // Submit URL
        .defaultSuccessUrl("/admin/login/success", true)
        .permitAll()
        .failureUrl("/admin/login/error");;

  }

  //mã hoá pass
  @Bean

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
