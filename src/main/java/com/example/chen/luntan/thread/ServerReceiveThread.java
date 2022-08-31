package com.example.chen.luntan.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.chen.luntan.pojo.UserNews;
import com.example.chen.luntan.service.Impl.NewsServiceImpl;
import com.example.chen.luntan.service.NewsService;
import com.example.chen.luntan.util.JwtUtil;
import com.example.chen.luntan.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;

public class ServerReceiveThread implements Runnable {

    private Socket socket;

    private static Logger log = LoggerFactory.getLogger(ServerReceiveThread.class);

    private NewsService newsService = SpringUtil.getBean(NewsServiceImpl.class);

    public ServerReceiveThread(Socket socket) {
        this.socket = socket;
    }

    public boolean verify(String token){
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

    public Long getUserId(String token){
        if(verify(token)){
            return JwtUtil.getToken(token).getClaim("id").asLong();
        }
        return Long.valueOf(-1);
    }

    @Override
    public void run() {
        try {
            //输入流接收数据
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //输出流发送数据
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            System.out.println(newsService);
            while (true) {
                JSONObject jsonObject = (JSONObject) ois.readObject();
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
                    oos.writeObject(result);
                    oos.flush();
                }else if (type.equals("close")) {
                    result.put("message","");
                    result.put("type","close");
                    oos.writeObject(result);
                    oos.flush();
                    break;
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
                    oos.writeObject(result);
                    oos.flush();
                }
            }
            log.info("服务端关闭客户端[{}]", socket.getRemoteSocketAddress());
            oos.close();
            ois.close();
            socket.close();
        } catch (Exception e) {
            log.info("接收数据异常socket关闭");
            e.printStackTrace();
        } finally {
            log.info("数据异常数据要怎么保留");
        }
    }
}

