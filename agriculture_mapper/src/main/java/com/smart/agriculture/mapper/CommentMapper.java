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
     * @param id 文章ID
     * @return 文章评论Vo
     */
    List<CommentVo> selectCommentByCode(String id);

    /**
     * 获取评论下评论
     * @param id 评论ID
     * @return 评论下内容List
     */
    List<SecondaryCommentVo> selectCommentById(String id);

    /**
     * 获取文章下的所有评论 ID
     * @param id 被评论人ID
     * @return 文章下所有评论ID
     */

    List<String> selectArticleAllComment(String id);

    /**
     * 获取单个评论
     * @param id 评论ID
     * @return 评论
     */

    Comment selectOneCommentById(String id);

    /**
     * 根据评论获取帖子作者Username
     * @param id 评论ID
     * @return 作者Username
     */

    String selectArticleUpUsernameByCommentId(String id);
}
