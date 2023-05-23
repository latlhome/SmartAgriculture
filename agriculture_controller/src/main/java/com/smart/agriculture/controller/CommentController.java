package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.Comment.AddCommentDto;
import com.smart.agriculture.Vo.Comment.CommentVo;
import com.smart.agriculture.Vo.Comment.SecondaryCommentVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "3==评论相关功能")
public class CommentController {
    @Resource
    private ICommentService commentService;
    @GetMapping("/selectCommentById")
    @ApiOperation("查看自由帖子回复详细")
    public CommonResult<PageVo<CommentVo>> selectArticleCommentById(ByIdPage page){
        return commentService.selectArticleCommentById(page);
    }

    @GetMapping("/selectComment/{id}")
    @ApiOperation("查看回复下的回复")
    public CommonResult<List<SecondaryCommentVo>> selectCommentById(@PathVariable("id") String id){
        return commentService.selectCommentById(id);
    }

    @DeleteMapping("/deleteComment/{id}")
    @ApiOperation("删除回复")
    public CommonResult<String> deleteComment(@PathVariable("id") String id){
        return commentService.deleteCommentById(id);
    }
    @PostMapping("/add")
    @ApiOperation("回复帖子/评论")
    public CommonResult<String> addComment(AddCommentDto dto){
        return commentService.addComment(dto);
    }

}

