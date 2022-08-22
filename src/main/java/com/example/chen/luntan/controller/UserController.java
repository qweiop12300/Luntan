package com.example.chen.luntan.controller;

import com.example.chen.luntan.common.api.ApiResult;
import com.example.chen.luntan.pojo.User;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/getUserId")
    public ApiResult<UserData> getUserId(int id){
        return ApiResult.success(userService.getUserData(id));
    }

}
