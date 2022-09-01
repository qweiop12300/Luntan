package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private long id;
  private String account;
  private String password;
  private String email;
  private Boolean is_activation;
  private String activation_data;
}
