package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@Api(tags = "评论相关功能")
public class CommentController {
    @Resource
    private ICommentService commentService;
    @GetMapping("/selectCommentById")
    @ApiOperation("查看自由帖子回复详细")
    public CommonResult selectArticleCommentById(ByIdPage page){
        return commentService.selectArticleCommentById(page);
    }

    @GetMapping("/selectComment/{id}")
    @ApiOperation("查看回复下的回复")
    public CommonResult selectCommentById(@PathVariable("id") String id){
        return commentService.selectCommentById(id);
    }

    @DeleteMapping("/deleteComment/{id}")
    @ApiOperation("删除回复")
    public CommonResult deleteComment(@PathVariable("id") String id){
        return commentService.deleteCommentById(id);
    }

}

