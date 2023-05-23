package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.MessagesList;
import com.smart.agriculture.enums.MessagesList.MessageType;
import com.smart.agriculture.mapper.MessagesListMapper;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IMessagesListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-23
 */
@Service
public class MessagesListServiceImpl extends ServiceImpl<MessagesListMapper, MessagesList> implements IMessagesListService {
    @Resource
    private IIsVoidService isVoidService;
    @Override
    public void sendSystemMessage(String acceptNumber, String content) {
        String username = isVoidService.isLogin();
        MessagesList messagesList = new MessagesList();
        messagesList.setAcceptNumber(acceptNumber);
        messagesList.setContent(content);
        messagesList.setSendNumber(username);
        messagesList.setType(MessageType.system.getCode());
        baseMapper.insert(messagesList);
    }

    @Override
    public void sendLikeMessage(String acceptNumber ,String likeId, Long like) {
        MessagesList flag = baseMapper.selectOne(new QueryWrapper<MessagesList>().lambda()
                .eq(MessagesList::getOtherId, likeId)
                .eq(MessagesList::getType,MessageType.like.getCode()));
        if (like == 0) baseMapper.deleteById(flag.getId());
        if (ObjectUtil.isNull(flag)){
            MessagesList messagesList = new MessagesList();
            messagesList.setAcceptNumber(acceptNumber);
            messagesList.setLikeNmb(like);
            messagesList.setOtherId(likeId);
            messagesList.setType(MessageType.like.getCode());
            baseMapper.insert(messagesList);
        }else {
            MessagesList messagesList = new MessagesList();
            BeanUtil.copyProperties(flag,messagesList);
            flag.setLikeNmb(like);
            baseMapper.updateById(messagesList);
        }
    }

    @Override
    public void sendCommentMessage(String acceptNumber, String otherId, String content, Integer type) {
        MessagesList messagesList = new MessagesList();
        messagesList.setAcceptNumber(acceptNumber);
        messagesList.setContent(content);
        messagesList.setType(type);
        messagesList.setOtherId(otherId);
        baseMapper.insert(messagesList);
    }
}
