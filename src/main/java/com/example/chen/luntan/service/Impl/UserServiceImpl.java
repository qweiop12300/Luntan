package com.example.chen.luntan.service.Impl;

import com.example.chen.luntan.mapper.UserMapper;
import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.pojo.dto.UserDto;
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

    @Override
    public User registerUser(UserDto dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .account(dto.getAccount())
                .password(dto.getPassword())
                .activationData("dasdawdd")
                .build();
        if(userMapper.registerUser(user)>0){
            return user;
        }
        return null;
    }

    @Override
    public User loginUser(String account, String password) {
        return null;
    }
}
