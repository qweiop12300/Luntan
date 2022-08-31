package com.example.chen.luntan;

import com.alibaba.fastjson.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHeartThread implements Runnable{

    private String host;

    private int port;

    public ClientHeartThread(String host, int port){
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host,port);
            System.out.println("socket开启");
            //�����д����
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //������������
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            int i =0;
            while (true){

                Thread.sleep(10000);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type","getMessage");
                jsonObject.put("message","1");
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiYWRtaW5tbSIsImlkIjoxLCJleHAiOjE2NjE5NjUyNjR9.VWHHWJUqawBs037sPBxQZPOLMJVrB13j4uDSp7GkPaY");
                jsonObject.put("data",jsonObject2);
                System.out.println(jsonObject.toString());
                oos.writeObject(jsonObject);
                oos.flush();
                i++;
                JSONObject jsonObject1 = (JSONObject) ois.readObject();
                System.out.println("服务器响应"+jsonObject1.toString());
                if(jsonObject1.getString("type").equals("close")){
                    break;
                }
            }
            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


