package com.example.chen.luntan.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.chen.luntan.pojo.Message;
import com.example.chen.luntan.pojo.UserNews;
import com.example.chen.luntan.service.Impl.NewsServiceImpl;
import com.example.chen.luntan.service.Impl.UserServiceImpl;
import com.example.chen.luntan.service.NewsService;
import com.example.chen.luntan.service.UserService;
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
    private UserService userService = SpringUtil.getBean(UserServiceImpl.class);

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
                                List<UserNews> list = new ArrayList<>();
                                list.add(userNews);
                                Message message = new Message("send","新消息",new Message.Data(null,list));
                                socketChannel.write(StandardCharsets.UTF_8.encode(JSONObject.toJSONString(message)));
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
                System.out.println(map.toString());
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
                                System.out.println("yes");
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

                            Message message = JSONObject.parseObject(str,Message.class);
                            Message result = new Message();


                            if(message.getType().equals("init")){
                                Long userId = getUserId(message.getData().getToken());
                                if(userId!=-1){
                                    map.put(socketChannel,userId);
                                    List<UserNews> list = newsService.selectNews(userId);
                                    System.out.println(list);
                                    result.setType("send");
                                    result.setMessage("已连接");
                                    result.setData(new Message.Data(null,list));
                                    newsService.deleteNews(userId);
                                }else{
                                    result.setMessage("连接失败");
                                    result.setType("upToken");
                                    result.setData(null);
                                }
                                ByteBuffer writeBuffer = StandardCharsets.UTF_8.encode(JSONObject.toJSONString(result));
                                socketChannel.write(writeBuffer);
                            }
                            else if(message.getType().equals("send")){
                                UserNews userNews = message.getData().getNews().get(0);

                                result.setMessage("成功");
                                result.setType("mySend");

                                userNews.setUser_id(map.get(socketChannel).longValue());
                                userNews.setPost_id(0);
                                userNews.setCreate_date(new Timestamp(System.currentTimeMillis()));
                                userNews.setType(5);

                                userNews.setUser_data(userService.getUserData(userNews.getUser_id()));
                                userNews.setP_user_data(userService.getUserData(userNews.getProduce_user_id()));
                                userNews.setNews_type(newsService.getNewsType(userNews.getType()));

                                if(!map.containsValue(userNews.getProduce_user_id())){
                                    newsService.sendNews(userNews);
                                }else{
                                    messageMap.put(userNews,userNews.getProduce_user_id());
                                }
                                result.setData(new Message.Data(null,message.getData().getNews()));
                                ByteBuffer writeBuffer = StandardCharsets.UTF_8.encode(JSONObject.toJSONString(result));
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
                            System.out.println(socketChannel);
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

