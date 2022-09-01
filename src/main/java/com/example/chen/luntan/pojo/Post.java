package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  private long id;
  private long user_id;
  private UserDataConcise user_data;
  private long type_id;
  private PostType post_type;
  private String images;
  private String title;
  private String content;
  private long comments;
  private long collects;
  private long view;
  private long like;
  private java.sql.Timestamp create_date;
  private java.sql.Timestamp update_date;
  private Boolean top;
  private Boolean essence;
}
