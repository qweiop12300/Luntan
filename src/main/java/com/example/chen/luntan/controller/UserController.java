package com.example.chen.luntan.controller;

import com.example.chen.luntan.common.api.ApiResult;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.pojo.dto.UserDto;
import com.example.chen.luntan.service.Impl.UserServiceImpl;
import com.example.chen.luntan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController{

    @Autowired
    UserServiceImpl userService;


    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public ApiResult<UserData> loginUser(@RequestBody UserDto user){
        UserData userData = userService.loginUser(user.getAccount(),user.getPassword());
        System.out.println(userData);
        if(userData!=null){
            setToken(JwtUtil.getToken(userData.getUser_id(),user.getAccount()));
            return ApiResult.success(userData);
        }
        return ApiResult.failed();
    }

    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    public ApiResult<String> registerUser(@RequestBody UserDto user){
        return ApiResult.failed(userService.registerUser(user));
    }

}
