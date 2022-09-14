package com.example.chen.luntan.service.Impl;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.mapper.UserMapper;
import com.example.chen.luntan.pojo.*;
import com.example.chen.luntan.pojo.dto.UserDto;
import com.example.chen.luntan.service.NewsService;
import com.example.chen.luntan.service.UserService;
import com.example.chen.luntan.socket.ServerSocketConfig;
import com.example.chen.luntan.util.EmailUtil;
import com.example.chen.luntan.util.MD5Utils;
import com.example.chen.luntan.util.RandomStringUtil;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    NewsService newsService;

    @Autowired
    EmailUtil emailUtil;

    @Override
    public UserData getUserData(long id) {
        UserData userData = userMapper.getUserData(id);
        UserAttention userAttention = new UserAttention();
        userAttention.setUser_id(id);
        userData.setAttention_size(userMapper.selectUserAttention(userAttention).size());
        userAttention.setUser_id(0);
        userAttention.setFollowed_user_id(id);
        userData.setFan_size(userMapper.selectUserAttention(userAttention).size());
        return userData;
    }

    @Override
    public IErrorCode registerUser(UserDto dto) {
        User userExists = userMapper.getUser(dto.getAccount());
        if(userExists!=null){
            return ApiErrorCode.USER_EXISTS;
        }
        User user = User.builder()
                .email(dto.getEmail())
                .account(dto.getAccount())
                .password(MD5Utils.getPwd(dto.getPassword()))
                .activation_data(RandomStringUtil.getRandomString(12))
                .build();



        if(userMapper.insertUser(user)>0){
            emailUtil.sendMessage(user.getEmail(),"欢迎注册","激活链接: http://127.0.0.1:8081/activation?user_id="+user.getId()+"&activation_data="+user.getActivation_data());
            UserData userData = new UserData();
            userData.setUser_id(user.getId());
            userData.setAvatar("http://rhzldubg8.hd-bkt.clouddn.com/icon.jpg");
            userData.setName("用户"+RandomStringUtil.getRandomString(10));
            userData.setCreate_date(new Timestamp(System.currentTimeMillis()));
            userMapper.insertUserData(userData);
            return ApiErrorCode.SUCCESS;
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public UserData loginUser(String account, String password) {
        User user = userMapper.getUser(account);
        if(user!=null){
            if(user.getIs_activation()){
                if(user.getPassword().equals(MD5Utils.getPwd(password))){
                    return userMapper.getUserData(user.getId());
                }
            }
        }
        return null;
    }

    @Override
    public IErrorCode attention(long user_id,long followed_user_id) {
        UserAttention userAttention = new UserAttention();
        userAttention.setUser_id(user_id);
        userAttention.setFollowed_user_id(followed_user_id);
        if(userMapper.selectUserAttention(userAttention).size()>0){
            userMapper.deleteUserAttention(userAttention);
            return ApiErrorCode.CANCEL;
        }
        UserNews userNews = UserNews.builder()
                .user_id(user_id)
                .produce_user_id(followed_user_id)
                .post_id(0)
                .create_date(new Timestamp(System.currentTimeMillis()))
                .type(1)
                .build();
        ServerSocketConfig.put(userNews);
        if(userMapper.insertUserAttention(userAttention)>0)return ApiErrorCode.SUCCESS;
        return ApiErrorCode.FAILED;
    }


    @Override
    public List<UserAttention> selectUserAttention(UserAttention userAttention) {
        return userMapper.selectUserAttention(userAttention);
    }

    @Override
    public IErrorCode activation(long user_id, String activation_data) {
        if(userMapper.selectActivation(user_id,activation_data).size()>0){
            userMapper.updateIsActivation(user_id);
            return ApiErrorCode.SUCCESS;
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public IErrorCode updateUserData(UserData userData) {
        if(userData.getUser_id()==0)return ApiErrorCode.FORBIDDEN;
        if(userMapper.updateUserData(userData)>0)return ApiErrorCode.SUCCESS;
        return ApiErrorCode.FAILED;
    }

    @Override
    public QiNiuToken getQiNiuToken(long user_id) {
        UserData userData = userMapper.getUserData(user_id);
        QiNiuToken qiNiuToken = new QiNiuToken();
        if(userData!=null){
            qiNiuToken.setUserData(userData);
            String accessKey = "C6SLZ4l0IccGjUHkLjyxqPhH7kC6LmctKlg3OBNd";
            String secretKey = "rOudexZ8NK-_0vEFclVZGiqeWNw4DrT8InAtg0dd";
            String bucket = "qfbbs";
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            qiNiuToken.setToken(upToken);
        }
        return qiNiuToken;
    }
}
