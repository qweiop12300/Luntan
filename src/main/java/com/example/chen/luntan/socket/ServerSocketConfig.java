package com.example.chen.luntan.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

public class ServerSocketConfig {

    private static Logger log = LoggerFactory.getLogger(ServerSocketConfig.class);

    private NewsService newsService = SpringUtil.getBean(NewsServiceImpl.class);

    public static Map<SocketChannel,Long> map = new Hashtable<SocketChannel,Long>();

    public static Map<UserNews,Long> messageMap = new Hashtable<UserNews,Long>();

    public void socketCreate() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, 0, null);
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(5301));
        log.info("socket开启成功:5301");
        while (true) {

            if(messageMap.size()!=0){
                System.out.println("-----------------------");
                System.out.println(messageMap.toString());
                System.out.println(map.toString());
                for(UserNews userNews:messageMap.keySet()){
                    Long id = messageMap.get(userNews);
                    if(map.containsValue(id)){
                        for (SocketChannel socketChannel:map.keySet()){
                            if(map.get(socketChannel).equals(id)){
                                JSONObject result = new JSONObject();
                                result.put("message","新消息");
                                result.put("type","send");
                                JSONObject jsonObject1 = new JSONObject();
                                JSONArray jsonArray = new JSONArray();
                                jsonArray.add(JSON.toJSON(userNews));
                                jsonObject1.put("news",jsonArray);
                                result.put("data",jsonObject1);
                                socketChannel.write(StandardCharsets.UTF_8.encode(result.toString()));
                                messageMap.remove(userNews);
                            }
                        }
                    }else{
                        newsService.sendNews(userNews);
                        messageMap.remove(userNews);
                    }

                }
            }


            if(selector.selectNow()!=0){
                System.out.println("开始处理事件=================");
                // 处理事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    //获取到SelectionKey对象之后，将集合内的引用删掉（Selecotr不会自动删除）
                    iterator.remove();
                    if (sk.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) sk.channel();
                        SocketChannel socketChannel = channel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                    } else if (sk.isReadable()) {//可读事件
                        SocketChannel socketChannel = null;
                        try {
                            //取到Channel
                            socketChannel = (SocketChannel) sk.channel();
                            //获取到绑定的ByteBuffer
                            ByteBuffer byteBuffer = (ByteBuffer) sk.attachment();
                            int read = socketChannel.read(byteBuffer);
                            //如果是正常断开 read = -1
                            if (read == -1) {
                                //取消事件
                                if(map.containsKey(socketChannel)){
                                    map.remove(socketChannel);
                                }
                                sk.cancel();
                                continue;
                            }

                            byteBuffer.flip();

                            String str = StandardCharsets.UTF_8.decode(byteBuffer).toString();

                            byteBuffer.rewind();
                            byteBuffer.limit(byteBuffer.capacity());

                            JSONObject jsonObject = JSONObject.parseObject(str);
                            JSONObject result = new JSONObject();
                            String type = jsonObject.getString("type");

                            if(type.equals("init")){
                                Long userId = getUserId(jsonObject.getJSONObject("data").getString("token"));
                                if(userId!=-1){
                                    map.put(socketChannel,userId);
                                    List<UserNews> list = newsService.selectNews(userId);
                                    result.put("message","已连接");
                                    result.put("type","send");
                                    JSONObject jsonObject1 = new JSONObject();
                                    jsonObject1.put("news",JSON.toJSON(list));
                                    result.put("data",jsonObject1);
                                    newsService.deleteNews(userId);
                                }else{
                                    result.put("message","连接失败");
                                    result.put("type","upToken");
                                    result.put("data",null);
                                }
                                ByteBuffer writeBuffer = StandardCharsets.UTF_8.encode(result.toString());
                                socketChannel.write(writeBuffer);
                            }
                            else if(type.equals("send")){
                                JSONObject jsonObjectData = jsonObject.getJSONObject("data").getJSONArray("news").getJSONObject(0);

                                result.put("message","成功");
                                result.put("type","mySend");

                                UserNews userNews = new UserNews();
                                userNews.setUser_id(map.get(socketChannel).longValue());
                                userNews.setProduce_user_id(jsonObjectData.getLong("produce_user_id"));
                                userNews.setPost_id(0);
                                userNews.setCreate_date(new Timestamp(System.currentTimeMillis()));
                                userNews.setType(5);
                                userNews.setContent(jsonObjectData.getString("content"));

                                if(!map.containsValue(jsonObjectData.getLong("produce_user_id"))){
                                    newsService.sendNews(userNews);
                                }else{
                                    messageMap.put(userNews,jsonObjectData.getLong("produce_user_id"));
                                }
                                result.put("news", JSON.toJSON(userNews));
                                ByteBuffer writeBuffer = StandardCharsets.UTF_8.encode(result.toString());
                                socketChannel.write(writeBuffer);
                            }else {
                                if(map.containsKey(socketChannel)){
                                    map.remove(socketChannel);
                                }
                                sk.cancel();
                                continue;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            //客户端异常断开连接 ，取消事件
                            if (socketChannel!=null){
                                if(map.containsKey(socketChannel)){
                                    map.remove(socketChannel);
                                }
                            }
                            sk.cancel();
                        }
                    }
                }
            }
        }
    }

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
}

