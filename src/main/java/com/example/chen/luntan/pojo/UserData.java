package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

  private long user_id;
  private String avatar;
  private String name;
  private Boolean sex;
  private long type;
  private String user_describe;
  private java.sql.Timestamp create_date;

}
