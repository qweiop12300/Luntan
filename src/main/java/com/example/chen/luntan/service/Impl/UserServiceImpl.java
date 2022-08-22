package com.example.chen.luntan.service.Impl;

import com.example.chen.luntan.mapper.UserMapper;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserData getUserData(int id) {
        return userMapper.getUserData(id);
    }
}
