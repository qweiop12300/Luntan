package com.example.chen.luntan.mapper;


import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserAttention;
import com.example.chen.luntan.pojo.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    User getUser(String account);

    List<UserData> getUserDataList(List<Long> list);

    UserData getUserData(long id);

    int insertUser(User user);

    int insertUserData(UserData userData);

    int insertUserAttention(UserAttention userAttention);

    int deleteUserAttention(UserAttention userAttention);

    List<UserAttention> selectUserAttention(UserAttention userAttention);

    List<User> selectActivation(long user_id, String activation_data);

    int updateIsActivation(long user_id);

    int updateUserData(UserData userData);
}
