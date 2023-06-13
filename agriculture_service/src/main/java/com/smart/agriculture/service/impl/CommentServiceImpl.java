package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.Comment;
import com.smart.agriculture.Do.FreedomArticle;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.Comment.AddCommentDto;
import com.smart.agriculture.Vo.Comment.CommentVo;
import com.smart.agriculture.Vo.Comment.SecondaryCommentVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.Comment.BeCommentedType;
import com.smart.agriculture.enums.MessagesList.MessageType;
import com.smart.agriculture.enums.SysUser.UserType;
import com.smart.agriculture.mapper.CommentMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.ICommentService;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IMessagesListService;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private IIsVoidService isVoidService;
    @Resource
    private IMessagesListService messagesListService;

    @Override
    public CommonResult<PageVo<CommentVo>> selectArticleCommentById(ByIdPage page) {
        PageVo<CommentVo> pageVo= new PageVo<>();
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
    public CommonResult<List<SecondaryCommentVo>> selectCommentById(String id) {
        List<SecondaryCommentVo> comments = baseMapper.selectCommentById(id);
        return CommonResult.success(comments);
    }

    @Override
    public CommonResult<String> deleteCommentById(String id) {
        Comment comment = baseMapper.selectOneCommentById(id);
        if (ObjectUtil.isNull(comment)) return CommonResult.failed("该评论不存在");
        String username = isVoidService.isLogin();
        String ArticleUpUsername = baseMapper.selectArticleUpUsernameByCommentId(id);
        if (!Objects.equals(username, comment.getReleaseUsername()) && !Objects.equals(ArticleUpUsername, username)) {
            // 这个人不是帖子创建者，也不是评论创建者 查看是否是超级管理员
            SysUser sysUser = sysUserMapper.selectOneByUsername(username);
            if (!Objects.equals(sysUser.getUserType(), UserType.developer.getCode()))
                return CommonResult.failed("您没有权限删除此评论！");
        }
        int i = baseMapper.deleteById(id);
        if (i > 0) return CommonResult.success("删除成功！");
        else return CommonResult.failed("删除失败！");
    }

    @Override
    public CommonResult<String> addComment(AddCommentDto dto) {
        String username = isVoidService.isLogin();
        Comment comment = new Comment();
        BeanUtil.copyProperties(dto, comment);
        comment.setReleaseUsername(username);
        List<Integer> collect = Arrays.stream(BeCommentedType.values())
                .map(BeCommentedType::getCode)
                .distinct()
                .collect(Collectors.toList());
        if (!collect.contains(dto.getBeCommentedType())){
            return CommonResult.failed("不存在该被评论的类型");
        }
        int insert = baseMapper.insert(comment);
        if (insert > 0) {
            if (Objects.equals(dto.getBeCommentedType(), BeCommentedType.ARTICLE_TYPE.getCode())) {
                FreedomArticle freedomArticle = isVoidService.freedomArticleIsExist(Long.valueOf(dto.getBeCommentedCode()));
                if (Objects.isNull(freedomArticle)) return CommonResult.failed("该文章不存在！");
                messagesListService.sendCommentMessage(freedomArticle.getAuthorUsername(), String.valueOf(freedomArticle.getId()), dto.getContent(), MessageType.article.getCode());
            } else if (Objects.equals(dto.getBeCommentedType(), BeCommentedType.COMMENTED_TYPE.getCode())) {
                Comment commentFlag = isVoidService.commentIsExist(Long.valueOf(dto.getBeCommentedCode()));
                if (ObjectUtil.isNull(commentFlag)) return CommonResult.failed("该评论不存在!");
                messagesListService.sendCommentMessage(commentFlag.getReleaseUsername(), String.valueOf(commentFlag.getId()), dto.getContent(), MessageType.comment.getCode());
            }
            return CommonResult.success("回复成功！");
        }
        return CommonResult.failed("未知错误!");
    }
}
