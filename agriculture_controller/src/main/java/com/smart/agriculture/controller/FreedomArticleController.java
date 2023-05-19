package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.FreedomArticle.AddFreedomArticleDto;
import com.smart.agriculture.Dto.FreedomArticle.SelectFreedomArticleListDto;
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
@Api(tags = "自由帖子")
public class FreedomArticleController {
    @Resource
    private IFreedomArticleService freedomArticleService;
    @PostMapping("/addFreedomArticle")
    @ApiOperation("新建自由帖子")
    public CommonResult addFreedomArticle(@RequestBody AddFreedomArticleDto addFreedomArticleDto){
        return freedomArticleService.addFreedomArticle(addFreedomArticleDto);
    }

    @DeleteMapping("/deleteFreedomArticle/{id}")
    @ApiOperation("删除自由帖子")
    public CommonResult deleteFreedomArticle(@PathVariable("id") String id){
        return freedomArticleService.deleteFreedomArticle(id);
    }

    @GetMapping("/selectFreedomArticleList")
    @ApiOperation("查看自由帖子列表")
    public CommonResult selectFreedomArticleList(SelectFreedomArticleListDto dto){
        return freedomArticleService.selectFreedomArticleList(dto);
    }

    @GetMapping("/selectFreedomArticleById/{id}")
    @ApiOperation("查看自由帖子详细")
    public CommonResult selectFreedomArticleById(@PathVariable String id){
        return freedomArticleService.selectFreedomArticleById(id);
    }

}

