package com.example.chen.luntan.service;

import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.pojo.Post;
import com.example.chen.luntan.pojo.PostComments;
import com.example.chen.luntan.pojo.dto.PostCommentsDto;
import com.example.chen.luntan.pojo.dto.PostDto;

import java.util.List;

public interface PostService {
    public List<Post> getListPost(int pg,int pz,int type_id);

    public Post getPost(int id);

    public IErrorCode setPost(PostDto postDto);

    public IErrorCode setPostComments(PostCommentsDto postCommentsDto);

    public List<PostComments> getPostComments(int userId,int postId);

    public IErrorCode collects(int userId,int postId);

    public IErrorCode like(int userId,int postId);

    public IErrorCode commentsLike(int commentsId,int userId);

}
