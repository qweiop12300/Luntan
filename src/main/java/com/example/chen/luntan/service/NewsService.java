package com.example.chen.luntan.service;

import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.pojo.NewsType;
import com.example.chen.luntan.pojo.UserNews;

import java.sql.Timestamp;
import java.util.List;

public interface NewsService {
    IErrorCode sendNews(UserNews userNews);
    List<UserNews> selectNews(long produce_user_id);
    IErrorCode deleteNews(long produce_user_id);
    NewsType getNewsType(long id);

}
