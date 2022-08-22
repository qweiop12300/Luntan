package com.example.chen.luntan.mapper;


import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    UserData getUserData(int id);

    int registerUser(User user);

    User loginUser(String account, String password);
}
