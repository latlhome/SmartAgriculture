package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.MessagesList;
import com.smart.agriculture.Vo.MessagesList.MyCommentMessageListVo;
import com.smart.agriculture.Vo.MessagesList.MyLikeMessageListVo;
import com.smart.agriculture.Vo.MessagesList.MySystemMessageListVo;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-23
 */
public interface IMessagesListService extends IService<MessagesList> {
    /**
     * 发送系统消息
     * @param acceptNumber 接收者username
     * @param content 内容
     */
    void sendSystemMessage(String acceptNumber, String content);

    /**
     * 发送点赞消息
     * @param acceptNumber 接收者username
     * @param otherId 唯一标识符
     * @param like 点赞数量
     */
    void sendLikeMessage(String acceptNumber,String otherId, Long like);

    /**
     * 发送回复消息
     * @param acceptNumber 接收者username
     * @param otherId 唯一标识符
     * @param content 内容
     * @param type 类型
     */
    void sendCommentMessage(String acceptNumber,String otherId,String content,Integer type);

    /**
     * 查看自己消息列表
     * @return 消息列表
     */
    CommonResult<List<MySystemMessageListVo>> queryMyMesList();

    /**
     * 点赞消息列表
     * @return 列表
     */
    CommonResult<List<MyLikeMessageListVo>> queryLikeMes();

    /**
     * 查询自己回复消息
     * @return 回复消息
     */
    CommonResult<List<MyCommentMessageListVo>> queryCommentMes();

    /**
     * 更新未读状态
     * @return 操作状态
     */
    CommonResult<String> isRead(String id);
}
