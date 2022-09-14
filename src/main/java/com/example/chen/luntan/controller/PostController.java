package com.example.chen.luntan.controller;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.ApiResult;
import com.example.chen.luntan.pojo.Post;
import com.example.chen.luntan.pojo.PostComments;
import com.example.chen.luntan.pojo.PostType;
import com.example.chen.luntan.pojo.dto.PostCommentsDto;
import com.example.chen.luntan.pojo.dto.PostDto;
import com.example.chen.luntan.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController extends BaseController{

    @Autowired
    PostService postService;

    @RequestMapping(value = "/setPost",method = RequestMethod.POST)
    public ApiResult<String> setPost(@RequestBody PostDto postDto){
        long userId = getUserId();
        if(userId!=-1){
            postDto.setUserId(userId);
            return ApiResult.failed(postService.setPost(postDto));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }

    @RequestMapping(value = "/upPost",method = RequestMethod.POST)
    public ApiResult<String> upPost(@RequestBody PostDto postDto){
        long userId = getUserId();
        if(userId!=-1){
            postDto.setUserId(userId);
            return ApiResult.failed(postService.updatePost(postDto));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }

    @RequestMapping(value = "/dePost",method = RequestMethod.POST)
    public ApiResult<String> dePost(int postId){
        long userId = getUserId();
        if(userId!=-1){
            return ApiResult.failed(postService.deletePost((int) userId,postId));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }



    @RequestMapping(value = "/getPost",method = RequestMethod.POST)
    public ApiResult<Post> getPost(int postId){
        Long userId = getUserId();
        return ApiResult.success(postService.getPost(postId,userId==-1?0:userId));
    }


    @RequestMapping(value = "/getListPost",method = RequestMethod.POST)
    public ApiResult<List<Post>> getListPost(Integer pg, Integer pz, Integer type_id){
        if (pg==null||pz==null){
            return ApiResult.failed(ApiErrorCode.VALIDATE_FAILED);
        }
        long userId = getUserId();

        return ApiResult.success(postService.getListPost(pg, pz, type_id, userId==-1?0:userId));
    }

    @RequestMapping(value = "/setPostComments",method = RequestMethod.POST)
    public ApiResult<String> setPostComments(@RequestBody PostCommentsDto postCommentsDto){
        long userId = getUserId();
        if(userId!=-1){
            postCommentsDto.setUserId(userId);
            return ApiResult.failed(postService.setPostComments(postCommentsDto));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);

    }

    @RequestMapping(value = "/getPostComments",method = RequestMethod.POST)
    public ApiResult<List<PostComments>> getPostComments(int postId){
        return ApiResult.success(postService.getPostComments(0,postId));
    }

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    public ApiResult<String> like(int postId){
        long userId = getUserId();
        if(userId!=-1){
            return ApiResult.failed(postService.like((int) userId,postId));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }

    @RequestMapping(value = "/collects",method = RequestMethod.POST)
    public ApiResult<String> collects(int postId){
        long userId = getUserId();
        if(userId!=-1){
            return ApiResult.failed(postService.collects((int) userId,postId));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }

    @RequestMapping(value = "/commentsLike",method = RequestMethod.POST)
    public ApiResult<String> commentsLike(int postId,int commentsId){
        long userId = getUserId();
        if(userId!=-1){
            return ApiResult.failed(postService.commentsLike(postId,commentsId, (int) userId));
        }
        return ApiResult.failed(ApiErrorCode.FORBIDDEN);
    }

    @RequestMapping(value = "/getPostType")
    public ApiResult<List<PostType>> getPostType(){
        return ApiResult.success(postService.getPostTypeList());
    }


}
