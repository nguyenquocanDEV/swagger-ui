package com.loginscreen.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user", schema = "public")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

  @Id
  @Column
  private String username;
  @Column
  private String password;
}