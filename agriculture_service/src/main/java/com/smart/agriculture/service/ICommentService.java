package com.smart.agriculture.service;

import com.smart.agriculture.Do.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Vo.Comment.CommentVo;
import com.smart.agriculture.Vo.Comment.SecondaryCommentVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 *  评论 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 查看自由帖子回复详细
     * @param page 分页
     * @return 帖子下的回复
     */
    CommonResult<PageVo<CommentVo>> selectArticleCommentById(ByIdPage page);

    /**
     * 查看回复下的回复
     * @param id 回复ID
     * @return 回复下的回复
     */
    CommonResult<List<SecondaryCommentVo>> selectCommentById(String id);

    /**
     * 删除回复
     * @param id 回复ID
     * @return 操作状态
     */

    CommonResult<String> deleteCommentById(String id);
}
