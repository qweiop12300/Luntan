package com.example.chen.luntan.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user_attention")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAttention {

  private long id;
  private long user_id;
  private UserData user_data;
  private long followed_user_id;
  private UserData fan_user_data;
}
