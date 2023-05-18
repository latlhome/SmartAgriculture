package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResult selectCommentById(ByIdPage page){
        return commentService.selectCommentById(page);
    }
}

