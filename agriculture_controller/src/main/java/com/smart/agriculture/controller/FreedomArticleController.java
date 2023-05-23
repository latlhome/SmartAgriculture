package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.FreedomArticle.AddFreedomArticleDto;
import com.smart.agriculture.Dto.FreedomArticle.QueryOfFollowDto;
import com.smart.agriculture.Dto.FreedomArticle.SelectFreedomArticleListDto;
import com.smart.agriculture.Dto.PageDto;
import com.smart.agriculture.Vo.FreedomArticle.ScrollResultVo;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleListVo;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IFreedomArticleService;
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
@RequestMapping("/freedomArticle")
@Api(tags = "3==自由帖子")
public class FreedomArticleController {
    @Resource
    private IFreedomArticleService freedomArticleService;
    @PostMapping("/addFreedomArticle")
    @ApiOperation("新建自由帖子")
    public CommonResult<String> addFreedomArticle(@RequestBody AddFreedomArticleDto addFreedomArticleDto){
        return freedomArticleService.addFreedomArticle(addFreedomArticleDto);
    }

    @DeleteMapping("/deleteFreedomArticle/{id}")
    @ApiOperation("删除自由帖子")
    public CommonResult<String> deleteFreedomArticle(@PathVariable("id") String id){
        return freedomArticleService.deleteFreedomArticle(id);
    }

    @GetMapping("/selectFreedomArticleList")
    @ApiOperation("查看自由帖子列表")
    public CommonResult<PageVo<SelectFreedomArticleListVo>> selectFreedomArticleList(SelectFreedomArticleListDto dto){
        return freedomArticleService.selectFreedomArticleList(dto);
    }
    @GetMapping("/queryFreedomArticleList")
    @ApiOperation("查看自由帖子列表2")
    public CommonResult<ScrollResultVo> queryFreedomArticleList(QueryOfFollowDto dto){
        return freedomArticleService.queryOfArticleList(dto);
    }

    @GetMapping("/selectFreedomArticleById/{id}")
    @ApiOperation("查看自由帖子详细")
    public CommonResult<SelectFreedomArticleVo> selectFreedomArticleById(@PathVariable String id){
        return freedomArticleService.selectFreedomArticleById(id);
    }

    @GetMapping("/queryOfFollow")
    @ApiOperation("查看我的关注下的帖子")
    public CommonResult<ScrollResultVo> queryOfFollow(QueryOfFollowDto dto){
        return freedomArticleService.queryOfFollow(dto);
    }
    @GetMapping("/queryOfCollection")
    @ApiOperation("查看我的收藏的帖子")
    public CommonResult<PageVo<SelectFreedomArticleListVo>> queryOfCollection(PageDto dto){
        return freedomArticleService.queryOfCollection(dto);
    }

}

