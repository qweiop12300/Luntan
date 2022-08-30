package com.example.chen.luntan.service;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.pojo.dto.UserDto;

import java.util.List;

public interface UserService {

    public UserData getUserData(int id);

    public IErrorCode registerUser(UserDto user);

    public UserData loginUser(String account, String password);

}
