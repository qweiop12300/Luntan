package com.example.chen.luntan;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientPrintThread implements Runnable{

    private String host;
    private int port;

    public ClientPrintThread(String host,int port){
        this.host = host;
        this.port = port;
    }
    @Override
    public void run() {
        try {
            Socket socket = new Socket(host,port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            while (true){
                String str = scanner.nextLine();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type","body");
                jsonObject.put("msg",str);
                oos.writeObject(jsonObject);
                oos.flush();
                String message = ois.readUTF();
                System.out.println("服务器响应"+message);
                if("close".equals(message)){
                    break;
                }
            }
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


