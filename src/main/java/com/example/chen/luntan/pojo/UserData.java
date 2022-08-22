package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

  private long userId;
  private String email;
  private String avatar;
  private String name;
  private String sex;
  private long type;
  private java.sql.Timestamp createDate;
  private String userDescribe;
}
