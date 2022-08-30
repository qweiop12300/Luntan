package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.ibatis.type.Alias;

@Alias("post_collects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCollects {

  private long user_id;
  private long post_id;

}
