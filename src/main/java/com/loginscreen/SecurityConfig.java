package com.loginscreen;


import com.loginscreen.model.User;
import com.loginscreen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;


@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

  BCryptPasswordEncoder pe = new BCryptPasswordEncoder();

  @Autowired
  UserRepository dao;


  //check
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
//    auth.userDetailsService(userServices); // Cung cáp userservice cho spring security
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
        .csrf().disable().cors().disable();
    http
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/css/**", "/").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/admin/login/form")
        .loginProcessingUrl("/j_spring_security_check") // Submit URL
        .defaultSuccessUrl("/login?success=true", true)
        .permitAll()
        .failureUrl("/admin/login/error");

    // ..0Auth2 - login fb
    http.oauth2Login()
        .loginPage("/admin/login/form")
        .defaultSuccessUrl("/oauth2/login/success", true)
        .failureUrl("/oauth2/login/error")
        //auth request
        .authorizationEndpoint()
        .baseUri("/admin/login/auth")
        .authorizationRequestRepository(getRepository())
        //auth response
        .and().tokenEndpoint()
        .accessTokenResponseClient(getToken());

  }

  @Bean
  public AuthorizationRequestRepository<OAuth2AuthorizationRequest> getRepository() {
    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> getToken() {
    return new DefaultAuthorizationCodeTokenResponseClient();
  }

  //mã hoá pass
  @Bean

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
