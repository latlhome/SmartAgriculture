package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        String username = isVoidService.isLogin();
        PageVo<CommentVo> pageVo = new PageVo<>();
        List<CommentVo> voList = new ArrayList<>();
        List<Comment> total = new ArrayList<>();
        IPage<Comment> commentIPage = baseMapper.selectPage(new Page<>(page.getPageNum(), page.getPageSize()), new QueryWrapper<Comment>().lambda()
                .eq(Comment::getBeCommentedCode, page.getId()));
        List<Long> ids = new ArrayList<>();
        for (Comment record : commentIPage.getRecords()) {
            ids.add(record.getId());
            total.add(record);
        }
        List<Comment> comments = baseMapper.selectList(new QueryWrapper<Comment>().lambda()
                .in(Comment::getBeCommentedCode, ids));
        total.addAll(comments);
        for (Comment comment : total) {
            CommentVo commentVo = new CommentVo();
            BeanUtil.copyProperties(comment,commentVo);
            SysUser sysUser = sysUserMapper.selectOneByUsername(comment.getReleaseUsername());
            commentVo.setCommentUserUsername(sysUser.getUsername());
            commentVo.setCommentUserPicture(sysUser.getHeadPicture());
            commentVo.setCommentUserNickname(sysUser.getNickname());
            if (comment.getReleaseUsername().equals(username)) commentVo.setOwner(true);
            voList.add(commentVo);
        }
        pageVo.setData(voList);
        pageVo.setTotalSize(commentIPage.getTotal());
        pageVo.setPageNum(commentIPage.getCurrent());
        pageVo.setTotalPages(commentIPage.getPages());
        pageVo.setPageSize(commentIPage.getSize());
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
        int insert = baseMapper.insert(comment);
        if (insert > 0) {
            if (Objects.equals(dto.getBeCommentedType(), BeCommentedType.ARTICLE_TYPE.getCode())) {
                FreedomArticle freedomArticle = isVoidService.freedomArticleIsExist(Long.valueOf(dto.getBeCommentedCode()));
                messagesListService.sendCommentMessage(freedomArticle.getAuthorUsername(), String.valueOf(freedomArticle.getId()), dto.getContent(), MessageType.article.getCode());
            } else if (Objects.equals(dto.getBeCommentedType(), BeCommentedType.COMMENTED_TYPE.getCode())) {
                Comment commentFlag = isVoidService.commentIsExist(Long.valueOf(dto.getBeCommentedCode()));
                messagesListService.sendCommentMessage(commentFlag.getReleaseUsername(), String.valueOf(commentFlag.getId()), dto.getContent(), MessageType.comment.getCode());
            }
            return CommonResult.success("回复成功！");
        }
        return CommonResult.failed("未知错误!");
    }
}
