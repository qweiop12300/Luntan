package com.example.chen.luntan.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Alias("news_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsType {

  private long id;
  private String title;
}
