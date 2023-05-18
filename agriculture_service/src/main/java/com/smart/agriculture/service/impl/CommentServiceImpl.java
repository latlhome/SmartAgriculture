package com.smart.agriculture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.Comment;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Vo.Comment.ArticleCommentVo;
import com.smart.agriculture.Vo.Comment.CommentVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.Comment.BeCmmentedType;
import com.smart.agriculture.mapper.CommentMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.ICommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public CommonResult selectCommentById(ByIdPage page) {
        //TODO
        ArticleCommentVo articleCommentVo = new ArticleCommentVo();
        List<CommentVo> commentVos = new ArrayList<>();
        LambdaQueryWrapper<Comment> lambda = new QueryWrapper<Comment>().lambda().eq(Comment::getBeCommentedCode, page.getId()).eq(Comment::getBeCommentedType, BeCmmentedType.ARTICLE_TYPE.getCode());
        IPage<Comment> commentIPage = baseMapper.selectPage(new Page<>(page.getPageNum(), page.getPageSize()), lambda);
        for (Comment record : commentIPage.getRecords()) {
            CommentVo commentVo = new CommentVo();
            SysUser sysUser = sysUserMapper.selectOneByUsername(record.getReleaseUsername());
            Integer integer = baseMapper.selectCount(new QueryWrapper<Comment>().lambda().eq(Comment::getBeCommentedCode, record.getId()).eq(Comment::getBeCommentedType, BeCmmentedType.COMMENTED_TYPE.getCode()));
            commentVo.setCommentNumber(integer);
            commentVo.setCommentUserUsername(record.getReleaseUsername());
            commentVo.setCommentUserPicture(sysUser.getHeadPicture());
            commentVo.setCommentUserNickname(sysUser.getNickname());
            commentVos.add(commentVo);
        }
        articleCommentVo.setCommentNumber((int) commentIPage.getTotal());
        articleCommentVo.setCommentVoList(commentVos);
        return CommonResult.success(articleCommentVo);
    }
}
