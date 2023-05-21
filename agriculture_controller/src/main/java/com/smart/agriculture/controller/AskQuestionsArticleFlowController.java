package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.AskQuestionsArticleFlow.AnswerQuestionDto;
import com.smart.agriculture.Dto.AskQuestionsArticleFlow.IsAdoptDto;
import com.smart.agriculture.Vo.AskQuestionsArticleFlow.GetAnswerByAskIdVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IAskQuestionsArticleFlowService;
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
@RequestMapping("/askQuestionsArticleFlow")
@Api(tags = "问答回复相关")
public class AskQuestionsArticleFlowController {
    @Resource
    private IAskQuestionsArticleFlowService baseService;
    @PostMapping("/answer")
    @ApiOperation("回答问题")
    public CommonResult<String> answerAskQuestion(@RequestBody AnswerQuestionDto dto){
        return baseService.answerAskQuestion(dto);
    }
    @GetMapping("/getAnswerByAskId/{id}")
    @ApiOperation("获取帖子下的回答 --传帖子ID")
    public CommonResult<List<GetAnswerByAskIdVo>> getAnswerByAskId(@PathVariable("id") Long id){
        return baseService.getAnswerByAskId(id);
    }
    @PutMapping("/isAdopt/{id}")
    @ApiOperation("修改采纳状态 --传回复ID")
    public CommonResult<String> isAdopt(@RequestBody IsAdoptDto dto){
        return baseService.isAdopt(dto);
    }

}

