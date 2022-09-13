package com.example.chen.luntan.pojo;


import java.util.List;

/**
 * 描述 :
 * 创建时间 : 2022/9/13 17:14
 * 作者 : 23128
 */
public class Message {
    String type;
    String message;
    Data data;

    public Message() {
    }

    public Message(String type) {
        this.type = type;
    }

    public Message(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public Message(String type, String message, Data data) {
        this.type = type;
        this.message = message;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        private String token;
        private List<UserNews> news;

        public Data(String token, List<UserNews> news) {
            this.token = token;
            this.news = news;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<UserNews> getNews() {
            return news;
        }

        public void setNews(List<UserNews> news) {
            this.news = news;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "token='" + token + '\'' +
                    ", news=" + news +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
