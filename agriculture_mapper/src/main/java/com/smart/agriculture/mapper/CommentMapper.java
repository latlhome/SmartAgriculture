package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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

    List<Comment> selectCommentByCode(String id);
}
