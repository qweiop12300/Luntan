package com.example.chen.luntan.service.Impl;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.mapper.NewsMapper;
import com.example.chen.luntan.mapper.UserMapper;
import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.pojo.dto.UserDto;
import com.example.chen.luntan.service.UserService;
import com.example.chen.luntan.util.MD5Utils;
import com.example.chen.luntan.util.RandomStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NewsMapper newsMapper;

    @Override
    public UserData getUserData(int id) {
        return userMapper.getUserData(id);
    }

    @Override
    public IErrorCode registerUser(UserDto dto) {
        User userExists = userMapper.getUser(dto.getAccount());
        if(userExists!=null){
            return ApiErrorCode.USER_EXISTS;
        }
        User user = User.builder()
                .email(dto.getEmail())
                .account(dto.getAccount())
                .password(MD5Utils.getPwd(dto.getPassword()))
                .activation_data(RandomStringUtil.getRandomString(12))
                .build();

        if(userMapper.insertUser(user)>0){
            UserData userData = new UserData();
            System.out.println(user);
            userData.setUser_id(user.getId());
            userData.setName("用户"+RandomStringUtil.getRandomString(10));
            userData.setCreate_date(new Timestamp(System.currentTimeMillis()));
            userMapper.insertUserData(userData);
            return ApiErrorCode.SUCCESS;
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public UserData loginUser(String account, String password) {
        User user = userMapper.getUser(account);
        if(user!=null){
            if(user.getPassword().equals(MD5Utils.getPwd(password))){
                return userMapper.getUserData(user.getId());
            }
        }
        return null;
    }
}
