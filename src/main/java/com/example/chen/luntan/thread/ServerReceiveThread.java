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

    public ServerReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //输入流接收数据
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //输出流发送数据
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                JSONObject jsonObject = (JSONObject) ois.readObject();

                JSONObject result = new ServerService().handle(jsonObject);
                if(result==null)break;
                oos.writeObject(result);
                oos.flush();
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

