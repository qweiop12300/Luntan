package com.example.chen.luntan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketTest {

    private static String host = "127.0.0.1";

    private static int port = 5301;

    static String sendId = "20";

    static String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiZGVtb3NzcyIsImlkIjoyMSwiZXhwIjoxNjYzMjQ5MDU2fQ.z0I2As60Ixq8-PKC2SiwZELn17NOArgPTZG63ltRlgA";

    public static void main(String[] args) throws Exception {
        client();

    }

    private static void client() throws Exception {
        //1.创建客户端
        SocketChannel socketChannel = SocketChannel.open();

        Thread.sleep(2000);
        //连接服务端
        socketChannel.connect(new InetSocketAddress("localhost",5301));
        //2 秒后写入数据

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
                StringBuffer stringBuffer = new StringBuffer();
                while (true){
                    try {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int i = socketChannel.read(byteBuffer);
                        if(i==1024){
                            byteBuffer.flip();
                            stringBuffer.append(StandardCharsets.UTF_8.decode(byteBuffer).toString());
                        }else{
                            byteBuffer.flip();
                            stringBuffer.append(StandardCharsets.UTF_8.decode(byteBuffer).toString());
                            System.out.println(JSONObject.parseObject(stringBuffer.toString()).toString());
                            stringBuffer.setLength(0);
                        }
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
            news.put("produce_user_id",sendId);
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
