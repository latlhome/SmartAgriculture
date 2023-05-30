package com.smart.agriculture.controller;


import com.smart.agriculture.Vo.MessagesList.MyCommentMessageListVo;
import com.smart.agriculture.Vo.MessagesList.MyLikeMessageListVo;
import com.smart.agriculture.Vo.MessagesList.MySystemMessageListVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IMessagesListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息列表
 * @author ylx
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/messagesList")
@Api(tags = "消息列表")
public class MessagesListController {
    @Resource
    private IMessagesListService messagesListService;
    @GetMapping("/querySystemMes")
    @ApiOperation("查询我的所有系统消息")
    public CommonResult<List<MySystemMessageListVo>> querySystemMes(){
        return messagesListService.queryMyMesList();
    }
    @GetMapping("/queryLikeMes")
    @ApiOperation("查询我的所有点赞消息")
    public CommonResult<List<MyLikeMessageListVo>> queryLikeMes(){
        return messagesListService.queryLikeMes();
    }
    @GetMapping("/queryCommentMes")
    @ApiOperation("查询我的所有评论消息")
    public CommonResult<List<MyCommentMessageListVo>> queryCommentMes(){
        return messagesListService.queryCommentMes();
    }
    @PostMapping("/isRead/{id}")
    @ApiOperation("更新未读状态")
    public CommonResult<String> isRead(@PathVariable("id") String id){
        return messagesListService.isRead(id);
    }
}

