package com.example.chen.luntan.service.Impl;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.mapper.NewsMapper;
import com.example.chen.luntan.mapper.PostMapper;
import com.example.chen.luntan.pojo.Post;
import com.example.chen.luntan.pojo.PostComments;
import com.example.chen.luntan.pojo.UserNews;
import com.example.chen.luntan.pojo.dto.PostCommentsDto;
import com.example.chen.luntan.pojo.dto.PostDto;
import com.example.chen.luntan.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostMapper postMapper;

    @Autowired
    NewsMapper newsMapper;

    @Override
    public List<Post> getListPost(int pg, int pz, int type_id) {
        List<Post> postList = postMapper.selectPost(pg*pz,pz*(pg+1),type_id);
        System.out.println(postList);
        return postList;
    }

    @Override
    public Post getPost(int id) {
        return null;
    }

    @Override
    public IErrorCode setPost(PostDto postDto) {
        Post post = new Post();
        post.setImages(postDto.getImages());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post.setCreate_date(timestamp);
        post.setUpdate_date(timestamp);
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setType_id(postDto.getTypeId());
        post.setUser_id(postDto.getUserId());
        if(postMapper.insertPost(post)>0)return ApiErrorCode.SUCCESS;
        return ApiErrorCode.FAILED;
    }

    @Override
    public IErrorCode setPostComments(PostCommentsDto postCommentsDto) {
        PostComments postComments = new PostComments();
        postComments.setPost_id(postCommentsDto.getPostId());
        postComments.setTitle(postCommentsDto.getTitle());
        postComments.setUser_id(postCommentsDto.getUserId());
        postComments.setReply_id(postCommentsDto.getReplyId());
        postComments.setCreate_date(new Timestamp(System.currentTimeMillis()));
        if(postMapper.comments(postComments)>0){
            UserNews userNews = new UserNews();
            userNews.setPost_id(postCommentsDto.getPostId());
            userNews.setUser_id(postCommentsDto.getUserId());
            userNews.setProduce_user_id(postMapper.getPostUserId(postCommentsDto.getPostId()));
            userNews.setType(2);
            userNews.setCreate_date(new Timestamp(System.currentTimeMillis()));
            newsMapper.insertNews(userNews);
            return ApiErrorCode.SUCCESS;
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public List<PostComments> getPostComments(int userId, int postId) {
        return postMapper.selectComments(userId,postId);
    }


}