package com.example.chen.luntan.service;

import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.pojo.dto.UserDto;

public interface UserService {

    public UserData getUserData(int id);

    public User registerUser(UserDto user);

    public User loginUser(String account,String password);

}
