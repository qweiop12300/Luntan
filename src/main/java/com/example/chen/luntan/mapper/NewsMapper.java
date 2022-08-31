package com.example.chen.luntan.mapper;

import com.example.chen.luntan.pojo.UserNews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NewsMapper {
    long insertNews(UserNews userNews);
}
