package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("comments_like")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsLike {

  private long post_id;
  private long comments_id;
  private long user_id;
}
