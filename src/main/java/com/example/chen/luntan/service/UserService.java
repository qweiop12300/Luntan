package com.example.chen.luntan.service;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.pojo.QiNiuToken;
import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserAttention;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.pojo.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserData getUserData(long id);

    public List<UserData> getUserDataList(List<Long> list);

    public IErrorCode registerUser(UserDto user);

    public UserData loginUser(String account, String password);

    public IErrorCode attention(long  user_id,long followed_user_id);

    public List<UserAttention> selectUserAttention(UserAttention userAttention);

    public IErrorCode activation(long user_id,String activation_data);

    public IErrorCode updateUserData(UserData userData);

    public QiNiuToken getQiNiuToken(long user_id);
}
