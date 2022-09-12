package com.example.chen.luntan.controller;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.ApiResult;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.pojo.QiNiuToken;
import com.example.chen.luntan.pojo.UserAttention;
import com.example.chen.luntan.pojo.UserData;
import com.example.chen.luntan.pojo.dto.UserDto;
import com.example.chen.luntan.service.Impl.UserServiceImpl;
import com.example.chen.luntan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController extends BaseController{

    @Autowired
    UserServiceImpl userService;


    @RequestMapping(value = "/loginUser",method = RequestMethod.POST)
    public ApiResult<UserData> loginUser(@RequestBody UserDto user){
        UserData userData = userService.loginUser(user.getAccount(),user.getPassword());
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

    @RequestMapping(value = "/upUserData",method = RequestMethod.POST)
    public ApiResult<String> upUserData(@RequestBody UserData userData){
        System.out.println(userData);
        Long userId = getUserId();
        if(userId!=-1){
            userData.setUser_id(userId);
            return ApiResult.failed(userService.updateUserData(userData));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }


    @RequestMapping(value = "/getMyUserData",method = RequestMethod.POST)
    public ApiResult<UserData> getMyUserData(){
        Long userId = getUserId();
        if(userId!=-1)return ApiResult.success(userService.getUserData(userId));
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }

    @RequestMapping(value = "/getUserData",method = RequestMethod.POST)
    public ApiResult<UserData> getUserData(long userId){
        return ApiResult.success(userService.getUserData(userId));
    }


    @RequestMapping(value = "/attention",method = RequestMethod.POST)
    public ApiResult<String> attention(long followed_user_id){
        Long userId = getUserId();
        if(userId!=-1)return ApiResult.failed(userService.attention(userId,followed_user_id));
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }

    @RequestMapping(value = "/getUserAttention",method = RequestMethod.POST)
    public ApiResult<List<UserAttention>> getUserAttention(@RequestBody UserAttention userAttention){
        if (userAttention.getUser_id()==-1){
            System.out.println(userAttention.toString());
            long userId = getUserId();
            if(userId!=-1){
                if(userAttention.getFollowed_user_id()==-1){
                    userAttention.setUser_id(0);
                    userAttention.setFollowed_user_id(userId);
                }else{
                    userAttention.setUser_id(userId);
                }
            }else{
                return ApiResult.failed(ApiErrorCode.FORBIDDEN);
            }
        }
        System.out.println(userAttention);
        return ApiResult.success(userService.selectUserAttention(userAttention));
    }

    @RequestMapping(value = "/activation",method = RequestMethod.POST)
    public ApiResult<String> activation(long user_id,String activation_data){
        return ApiResult.failed(userService.activation(user_id,activation_data));
    }


    @RequestMapping(value = "/getQiNiuToken",method = RequestMethod.POST)
    public ApiResult<QiNiuToken> getQiNiuToken(){
        long userId = getUserId();
        return ApiResult.success(userService.getQiNiuToken(userId==-1?0:userId));
    }
}
