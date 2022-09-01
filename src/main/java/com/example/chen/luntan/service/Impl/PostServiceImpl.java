package com.example.chen.luntan.service.Impl;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.IErrorCode;
import com.example.chen.luntan.mapper.PostMapper;
import com.example.chen.luntan.pojo.*;
import com.example.chen.luntan.pojo.dto.PostCommentsDto;
import com.example.chen.luntan.pojo.dto.PostDto;
import com.example.chen.luntan.service.NewsService;
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
    NewsService newsService;

    @Override
    public List<Post> getListPost(int pg, int pz, int type_id) {
        List<Post> postList = postMapper.selectPost(pg*pz,pz*(pg+1),type_id);
        System.out.println(postList);
        return postList;
    }

    @Override
    public Post getPost(int id) {
        postMapper.addPostView(id);
        return postMapper.getPost(id);
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
    public IErrorCode updatePost(PostDto postDto) {
        if(postDto.getUserId()==0||postDto.getId()==0){
            return ApiErrorCode.VALIDATE_FAILED;
        }
        Post post = postMapper.getPost(postDto.getId());
        System.out.println(post);
        if (post.getUser_id()==postDto.getUserId()){
            post.setImages(postDto.getImages());
            post.setUpdate_date(new Timestamp(System.currentTimeMillis()));
            post.setContent(postDto.getContent());
            post.setTitle(postDto.getTitle());
            post.setType_id(postDto.getTypeId());
            if (postMapper.updatePost(post)>0)return ApiErrorCode.SUCCESS;
            return ApiErrorCode.FAILED;
        }
        return ApiErrorCode.FORBIDDEN;
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
            postMapper.addPostComments((int) postCommentsDto.getPostId(),1);
            UserNews userNews = UserNews.builder()
                    .user_id(postCommentsDto.getUserId())
                    .produce_user_id(postMapper.getPostUserId(postCommentsDto.getPostId()))
                    .post_id(postCommentsDto.getPostId())
                    .create_date(new Timestamp(System.currentTimeMillis()))
                    .type(2)
                    .build();
            newsService.sendNews(userNews);
            return ApiErrorCode.SUCCESS;
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public List<PostComments> getPostComments(int userId, int postId) {
        return postMapper.selectComments(userId,postId);
    }

    @Override
    public IErrorCode collects(int userId,int postId) {
        PostCollects postCollects = new PostCollects();
        postCollects.setPost_id(postId);
        postCollects.setUser_id(userId);
        if(postMapper.selectCollects(userId,postId).size()>0){
            postMapper.addPostCollects(postId,0);
            if(postMapper.deleteCollects(postCollects)>0)return ApiErrorCode.CANCEL;
        }
        else {
            if(postMapper.collects(postCollects)>0){
                postMapper.addPostCollects(postId,1);
                UserNews userNews = UserNews.builder()
                        .user_id(userId)
                        .produce_user_id(postMapper.getPostUserId(postId))
                        .post_id(postId)
                        .create_date(new Timestamp(System.currentTimeMillis()))
                        .type(4)
                        .build();
                newsService.sendNews(userNews);

                return ApiErrorCode.SUCCESS;
            }
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public IErrorCode like(int userId,int postId) {
        PostLike  postLike = new PostLike();
        postLike.setPost_id(postId);
        postLike.setUser_id(userId);
        if(postMapper.selectLike(userId,postId).size()>0){
            postMapper.addPostLike(postId,0);
            if(postMapper.deleteLike(postLike)>0)return ApiErrorCode.CANCEL;
        }
        else {
            if(postMapper.like(postLike)>0){
                postMapper.addPostLike(postId,1);
                UserNews userNews = UserNews.builder()
                        .user_id(userId)
                        .produce_user_id(postMapper.getPostUserId(postId))
                        .post_id(postId)
                        .create_date(new Timestamp(System.currentTimeMillis()))
                        .type(3)
                        .build();
                newsService.sendNews(userNews);

                return ApiErrorCode.SUCCESS;
            }
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public IErrorCode commentsLike(int postId,int commentsId, int userId) {
        CommentsLike commentsLike = new CommentsLike();
        commentsLike.setComments_id(commentsId);
        commentsLike.setUser_id(userId);
        commentsLike.setPost_id(postId);
        if(postMapper.selectCommentsLike(userId,commentsId).size()>0){
            postMapper.addPostCommentsLike(commentsId,0);
            if(postMapper.deleteCommentsLike(commentsLike)>0)return ApiErrorCode.CANCEL;
        }
        else {
            if(postMapper.commentsLike(commentsLike)>0){
                postMapper.addPostCommentsLike(commentsId,1);
                UserNews userNews = UserNews.builder()
                        .user_id(userId)
                        .produce_user_id(postMapper.getCommentsUserId(commentsId))
                        .post_id(postMapper.getCommentsPostId(commentsId))
                        .create_date(new Timestamp(System.currentTimeMillis()))
                        .type(4)
                        .build();
                newsService.sendNews(userNews);

                return ApiErrorCode.SUCCESS;
            }
        }
        return ApiErrorCode.FAILED;
    }

    @Override
    public List<PostType> getPostTypeList() {
        return postMapper.selectPostType();
    }

    @Override
    public IErrorCode deletePost(int user_id,int post_id) {
        Post post = postMapper.getPost(post_id);
        if (post.getUser_id()==user_id){
            postMapper.deletePost(post_id);
            return ApiErrorCode.SUCCESS;
        }
        return ApiErrorCode.VALIDATE_FAILED;
    }
}
