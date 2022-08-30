package com.example.chen.luntan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("post_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostType {

  private long id;
  private String title;

}
