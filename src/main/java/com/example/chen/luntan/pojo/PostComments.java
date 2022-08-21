package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostComments {

  private long id;
  private long userId;
  private long postId;
  private long replyId;
  private java.sql.Timestamp createDate;
  private long like;
  private String title;

}
