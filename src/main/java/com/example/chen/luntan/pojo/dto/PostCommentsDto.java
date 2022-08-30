package com.example.chen.luntan.pojo.dto;

import lombok.Data;

@Data
public class PostCommentsDto {

    private long userId;
    private long postId;
    private long replyId;
    private String title;
}
