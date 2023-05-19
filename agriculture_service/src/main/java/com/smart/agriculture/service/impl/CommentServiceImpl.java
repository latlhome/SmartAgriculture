package com.smart.agriculture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.Comment;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Vo.Comment.CommentVo;
import com.smart.agriculture.Vo.Comment.SecondaryCommentVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.mapper.CommentMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.ICommentService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public CommonResult selectArticleCommentById(ByIdPage page) {
        PageVo pageVo= new PageVo();
        List<CommentVo> commentVos = baseMapper.selectCommentByCode(page.getId());
        PagedListHolder<CommentVo> pagedListHolder = new PagedListHolder<>(commentVos);
        pagedListHolder.setPageSize(Math.toIntExact(page.getPageSize()));
        pagedListHolder.setPage(Math.toIntExact(page.getPageNum()));
        pageVo.setData(pagedListHolder.getPageList());
        pageVo.setTotalSize(commentVos.size());
        pageVo.setPageNum(page.getPageNum());
        pageVo.setTotalPages((commentVos.size()+page.getPageSize()-1)/page.getPageSize());
        pageVo.setPageSize(page.getPageSize());
        return CommonResult.success(pageVo);
    }

    @Override
    public CommonResult selectCommentById(String id) {
        List<SecondaryCommentVo> comments = baseMapper.selectCommentById(id);
        return CommonResult.success(comments);
    }
}
