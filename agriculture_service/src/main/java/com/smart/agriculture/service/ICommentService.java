package com.smart.agriculture.service;

import com.smart.agriculture.Do.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  评论 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface ICommentService extends IService<Comment> {

    CommonResult selectCommentById(ByIdPage page);
}
