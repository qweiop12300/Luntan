package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("user_news")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserNews {

  private long id;
  private long user_id;
  private long produce_user_id;
  private long type;
  private long post_id;
  private java.sql.Timestamp create_date;
}
