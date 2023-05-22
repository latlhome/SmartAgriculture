package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.AskQuestionsArticle.AddAskQuestionsArticleDto;
import com.smart.agriculture.Dto.AskQuestionsArticle.GetAllListDto;
import com.smart.agriculture.Dto.AskQuestionsArticle.UpdateAskQuestionsArticleDto;
import com.smart.agriculture.Vo.AskQuestionsArticle.GetAllListVo;
import com.smart.agriculture.Vo.AskQuestionsArticle.GetOneVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IAskQuestionsArticleService;
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
@RequestMapping("/askQuestionsArticle")
@Api(tags = "4==问答帖子相关")
public class AskQuestionsArticleController {
    @Resource
    private IAskQuestionsArticleService baseService;

    @PostMapping("/add")
    @ApiOperation("创建问题")
    public CommonResult<String> addAskQuestionsArticle(@RequestBody AddAskQuestionsArticleDto dto){
        return baseService.addAskQuestionsArticle(dto);
    }
    @PutMapping("/update")
    @ApiOperation("更改问题")
    public CommonResult<String> updateAskQuestionsArticle(@RequestBody UpdateAskQuestionsArticleDto dto){
        return baseService.updateAskQuestionsArticle(dto);
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除问题")
    public CommonResult<String> deleteAskQuestionsArticle(@PathVariable("id") Long id){
        return baseService.deleteAskQuestionsArticle(id);
    }
    @GetMapping("/getAllList")
    @ApiOperation("查询问答列表")
    public CommonResult<PageVo<GetAllListVo>> getAllList(GetAllListDto dto){
        return baseService.getAllList(dto);
    }
    @GetMapping("/getOne/{id}")
    @ApiOperation("查询问答")
    public CommonResult<GetOneVo> getOne(@PathVariable("id") Long id){
        return baseService.getOne(id);
    }
}

