package com.example.chen.luntan.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.chen.luntan.pojo.UserNews;
import com.example.chen.luntan.service.Impl.NewsServiceImpl;
import com.example.chen.luntan.service.NewsService;
import com.example.chen.luntan.util.JwtUtil;
import com.example.chen.luntan.util.SpringUtil;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;

public class ServerService {

    private NewsService newsService = SpringUtil.getBean(NewsServiceImpl.class);

    private static boolean verify(String token){
        if (StringUtils.isEmpty(token)){
            return false;
        }
        try {
            JwtUtil.verify(token);
        }catch (TokenExpiredException e){
            return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private static Long getUserId(String token){
        if(verify(token)){
            return JwtUtil.getToken(token).getClaim("id").asLong();
        }
        return Long.valueOf(-1);
    }

    public JSONObject handle(JSONObject jsonObject){
        JSONObject result = new JSONObject();
        String message = jsonObject.getString("message");
        String type = jsonObject.getString("type");

        if(type.equals("getMessage")){
            JSONObject jsonObjectData = jsonObject.getJSONObject("data");
            Long userId = getUserId(jsonObjectData.getString("token"));
            result.put("message",String.valueOf(Long.valueOf(message)+1));
            result.put("type","getMessage");
            if(userId!=-1){
                result.put("data", JSON.toJSON(newsService.selectNews(userId)));
                newsService.deleteNews(userId);
            }else{
                result.put("data",null);
            }

        }else if (type.equals("close")) {
            result.put("message","");
            result.put("type","close");
            return null;
        } else {
            JSONObject jsonObjectData = jsonObject.getJSONObject("data");
            Long userId = getUserId(jsonObjectData.getString("token"));
            result.put("message",message);
            result.put("type","send");

            if(userId!=-1){
                UserNews userNews = new UserNews();
                userNews.setUser_id(userId);
                userNews.setProduce_user_id(jsonObjectData.getLong("produce_user_id"));
                userNews.setPost_id(0);
                userNews.setCreate_date(new Timestamp(System.currentTimeMillis()));
                userNews.setType(5);
                userNews.setContent(message);
                newsService.sendNews(userNews);
                result.put("data", JSON.toJSON(userNews));
            }else{
                result.put("data",null);
            }

        }
        System.out.println(result);
        return result;
    }
}
