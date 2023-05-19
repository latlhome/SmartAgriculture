package com.smart.agriculture.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.agriculture.Do.Comment;
import com.smart.agriculture.Vo.Comment.CommentVo;
import com.smart.agriculture.Vo.Comment.SecondaryCommentVo;

import java.util.List;

/**
 * <p>
 *  评论 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 获取文章下评论
     * @param id
     * @return
     */
    List<CommentVo> selectCommentByCode(String id);

    List<SecondaryCommentVo> selectCommentById(String id);

    List<String> selectArticleAllComment(String id);

    Comment selectOneCommentById(String id);

    String selectArticleUpUsernameByCommentId(String id);
}
