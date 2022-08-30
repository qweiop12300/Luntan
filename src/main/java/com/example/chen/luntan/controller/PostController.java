package com.example.chen.luntan.controller;

import com.example.chen.luntan.common.api.ApiErrorCode;
import com.example.chen.luntan.common.api.ApiResult;
import com.example.chen.luntan.pojo.Post;
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

    @RequestMapping(value = "/getListPost",method = RequestMethod.POST)
    public ApiResult<List<Post>> getListPost(Integer pg, Integer pz, Integer type_id){
        if (pg==null||pz==null){
            return ApiResult.failed(ApiErrorCode.VALIDATE_FAILED);
        }
        if (type_id==null){
            return ApiResult.success(postService.getListPost(pg, pz,-1));
        }else{
            return ApiResult.success(postService.getListPost(pg, pz, type_id));
        }
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

}
