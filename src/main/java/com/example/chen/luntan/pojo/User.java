package com.example.chen.luntan.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private long id;
  private String account;
  private String password;
  private String email;
  private String isActivation;
  private String avatar;
  private String name;
  private String sex;
  private String type;
  private java.sql.Timestamp createDate;
  private String describe;

}
