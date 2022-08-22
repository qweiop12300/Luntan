package com.example.chen.luntan.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.chen.luntan.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BaseController {

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public HttpServletResponse response;

    public int getUserId(HttpServletRequest request){
        String token = request.getHeader(JwtUtil.HEADER_TOKEN_KEY);
        return Integer.parseInt(JwtUtil.getToken(token).getClaim("id").asString());
    }

    public String getUsername(){
        String token = request.getHeader(JwtUtil.HEADER_TOKEN_KEY);
        return JwtUtil.getToken(token).getClaim("username").asString();
    }


    public void verify(String token){
        //验证token


    }


    public boolean isLogin(HttpServletRequest request){
        //获取请求头里的token
        String token = request.getHeader(JwtUtil.HEADER_TOKEN_KEY);

        if (StringUtils.isEmpty(token)){
            return false;
        }

        try {
            JwtUtil.verify(token);
        }catch (TokenExpiredException e){
            DecodedJWT decodedJWT = JwtUtil.getToken(token);
            Map<String ,String> map = new HashMap<>();
            map.put("id",decodedJWT.getClaim("id").asString());
            map.put("username",decodedJWT.getClaim("username").asString());
            response.addHeader("set-"+JwtUtil.HEADER_TOKEN_KEY,JwtUtil.getToken(map));
        }catch (Exception e){
            return false;
        }

        return true;
    }
}
