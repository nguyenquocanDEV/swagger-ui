package com.loginscreen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class User {

  @Id
  @Column(name = "username", nullable = false, length = 30)
  private String username;

  @Column(name = "password", nullable = false, length = 30)
  private String password;


}