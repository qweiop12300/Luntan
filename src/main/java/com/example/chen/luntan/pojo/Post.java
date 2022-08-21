package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  private long id;
  private long userId;
  private long typeId;
  private String images;
  private String title;
  private String content;
  private long comments;
  private long collects;
  private long view;
  private long like;
  private java.sql.Timestamp createDate;
  private java.sql.Timestamp updateDate;
  private String top;
  private String essence;
}
