package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("post_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostComments {

  private long id;
  private long user_id;
  private long post_id;
  private long reply_id;
  private java.sql.Timestamp create_date;
  private long like;
  private String title;

}
