package com.example.chen.luntan.service.Impl;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.mapper.NewsMapper;
import com.example.chen.luntan.pojo.NewsType;
import com.example.chen.luntan.pojo.UserNews;
import com.example.chen.luntan.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    NewsMapper newsMapper;

    @Override
    public IErrorCode sendNews(UserNews userNews) {
        if(newsMapper.insertNews(userNews)>0)return ApiErrorCode.SUCCESS;
        return ApiErrorCode.FAILED;
    }

    @Override
    public List<UserNews> selectNews(long produce_user_id) {
        return newsMapper.selectNews(produce_user_id);
    }


    @Override
    public IErrorCode deleteNews(long produce_user_id) {
        if(newsMapper.deleteNews(produce_user_id)>0)return ApiErrorCode.SUCCESS;
        return ApiErrorCode.FAILED;
    }

    @Override
    public NewsType getNewsType(long id) {
        return newsMapper.getNewsType(id);
    }
}
