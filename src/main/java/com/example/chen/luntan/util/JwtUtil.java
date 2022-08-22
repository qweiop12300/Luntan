package com.example.chen.luntan.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JwtUtil  {
    public static final String HEADER_TOKEN_KEY = "Authorization";
    private static final String  secret = "Chen";


    /**
     *生成token
     * @param map :playload信息
     * @return
     */
    public static String getToken(Map<String,String> map){
            JWTCreator.Builder builder = JWT.create();
            //放入负载信息
            map.forEach((k,v)->{
                System.out.println(k+"===========");
                System.out.println(v+"===========");
                builder.withClaim(k,v);
            });
            Calendar instance = Calendar.getInstance();
            //过期时间为2小时
            instance.add(Calendar.SECOND,60*60*2);
            builder.withExpiresAt(instance.getTime());
            return builder.sign(Algorithm.HMAC256(secret));
    }


    /**
     * 验证token
     * @param token
     * @return
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }


    /**
     * 获取token中payload,无需解密也可获得
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.decode(token);
    }
}
