package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private long id;
  private String account;
  private String password;
  private String email;
  private String isActivation;
  private String activationData;
}
