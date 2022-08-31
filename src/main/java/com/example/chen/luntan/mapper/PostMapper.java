package com.example.chen.luntan.mapper;

import com.example.chen.luntan.pojo.*;
import com.example.chen.luntan.pojo.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PostMapper {

    List<Post> selectPost(int first,int last,int type_id);

    int insertPost(Post post);

    int like(PostLike postLike);

    int collects(PostCollects postCollects);

    int comments(PostComments postComments);

    int commentsLike(CommentsLike commentsLike);

    int deleteCollects(PostCollects postCollects);

    int deleteLike(PostLike postLike);

    int deleteCommentsLike(CommentsLike commentsLike);

    List<PostLike> selectLike(long userId, long postId);

    List<PostCollects> selectCollects(long userId, long postId);

    List<PostComments> selectComments(long userId,long postId);

    List<CommentsLike> selectCommentsLike(long userId,long commentsId);

    int updatePost(Post post);
    int updateComments(PostComments postComments);

    long getPostUserId(long postId);

    long getCommentsUserId(long id);

    long getCommentsPostId(long id);

}
