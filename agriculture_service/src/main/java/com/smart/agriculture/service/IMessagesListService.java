package com.smart.agriculture.service;

import com.smart.agriculture.Do.MessagesList;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
