package com.example.chen.luntan.service;

import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.pojo.*;
import com.example.chen.luntan.pojo.dto.PostCommentsDto;
import com.example.chen.luntan.pojo.dto.PostDto;

import java.util.List;

public interface PostService {
    public List<Post> getListPost(int pg,int pz,int type_id,long user_id,long uid);

    public Post getPost(int id,long user_id);

    public IErrorCode setPost(PostDto postDto);

    public IErrorCode updatePost(PostDto postDto);

    public IErrorCode setPostComments(PostCommentsDto postCommentsDto);

    public List<PostComments> getPostComments(int userId,int postId);

    public List<PostCollects> getCollects(int userId, int postId);

    public List<PostLike> getLike(int userId,int postId);

    public IErrorCode collects(int userId,int postId);

    public IErrorCode like(int userId,int postId);

    public IErrorCode commentsLike(int post_id,int commentsId,int userId);

    public List<PostType> getPostTypeList();

    public IErrorCode deletePost(int user_id,int post_id);
}
