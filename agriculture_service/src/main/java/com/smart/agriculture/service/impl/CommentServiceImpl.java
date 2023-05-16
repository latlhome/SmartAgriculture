package com.smart.agriculture.service.impl;

import com.smart.agriculture.Do.Comment;
import com.smart.agriculture.mapper.CommentMapper;
import com.smart.agriculture.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  评论 服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
