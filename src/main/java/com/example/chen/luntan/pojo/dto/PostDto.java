package com.example.chen.luntan.pojo.dto;

import lombok.Data;

@Data
public class PostDto {
    private long userId;
    private long typeId;
    private String images;
    private String title;
    private String content;
}