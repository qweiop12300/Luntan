package com.example.chen.luntan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SocketTest2 {
    private static String host = "127.0.0.1";

    private static int port = 5301;

    static String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWRtaW5uIiwiaWQiOjE3LCJleHAiOjE2NjIxMjM1MTB9.5NfGrOAdYXy5ToeDapxVKe3UCUAuoAuBdHlODP5vaa8";

    public static void main(String[] args) throws Exception {
        client();

    }

    private static void client() throws Exception {
        //1.创建客户端
        SocketChannel socketChannel = SocketChannel.open();
        //连接服务端
        socketChannel.connect(new InetSocketAddress("localhost",5301));
        //2 秒后写入数据
        Thread.sleep(2 * 1000);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type","init");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("token",token);
        jsonObject.put("data",jsonObject1);
        socketChannel.write(StandardCharsets.UTF_8.encode(jsonObject.toString()));

        //3.读取服务端返回数据
        //断开连接
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        System.out.println("服务端返回数据=======：" + StandardCharsets.UTF_8.decode(byteBuffer).toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        while (true){

            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            JSONObject news = new JSONObject();
            news.put("content",s);
            news.put("produce_user_id","15");
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("type","send");
            JSONObject jsonObject4 = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(news);
            jsonObject4.put("news",jsonArray);
            jsonObject3.put("data",jsonObject4);
            System.out.println(jsonObject3.toString());
            socketChannel.write(StandardCharsets.UTF_8.encode(jsonObject3.toString()));
        }

//        socketChannel.close();
    }
}
