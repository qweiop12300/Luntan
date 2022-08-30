package com.example.chen.luntan.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user_news")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNews {

  private long id;
  private long user_id;
  private long produce_user_id;
  private long type;
  private long post_id;

}
