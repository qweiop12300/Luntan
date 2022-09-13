package com.example.chen.luntan.mapper;

import com.example.chen.luntan.pojo.NewsType;
import com.example.chen.luntan.pojo.UserNews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsMapper {
    long insertNews(UserNews userNews);
    List<UserNews> selectNews(long produce_user_id);
    long deleteNews(long produce_user_id);
    NewsType getNewsType(long id);
}
