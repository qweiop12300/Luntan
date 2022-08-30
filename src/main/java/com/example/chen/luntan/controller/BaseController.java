package com.example.chen.luntan.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.chen.luntan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BaseController {

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public HttpServletResponse response;

    public Long getUserId(){
        if (isLogin()){
            String token = request.getHeader(JwtUtil.HEADER_TOKEN_KEY);
            return JwtUtil.getToken(token).getClaim("id").asLong();
        }
        return Long.valueOf(-1);
    }

    public String getUsername(){
        if (isLogin()){
            String token = request.getHeader(JwtUtil.HEADER_TOKEN_KEY);
            return JwtUtil.getToken(token).getClaim("name").asString();
        }
        return null;
    }


    public String updataToken(String token){
        //验证token
        Long id = JwtUtil.getToken(token).getClaim("id").asLong();
        String  name = JwtUtil.getToken(token).getClaim("name").asString();
        return JwtUtil.getToken(id,name);
    }

    public void setToken(String token){
        response.setHeader("set-"+JwtUtil.HEADER_TOKEN_KEY,token);
    }


    public boolean isLogin(){
        //获取请求头里的token
        String token = request.getHeader(JwtUtil.HEADER_TOKEN_KEY);

        if (StringUtils.isEmpty(token)){
            return false;
        }
        try {
            JwtUtil.verify(token);
        }catch (TokenExpiredException e){
            setToken(updataToken(token));
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
