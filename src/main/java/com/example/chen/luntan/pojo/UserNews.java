package com.example.chen.luntan.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNews {

  private long id;
  private long userId;
  private long produceUserId;
  private long type;
  private long postId;

}
