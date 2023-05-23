package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.Comment;
import com.smart.agriculture.Do.FreedomArticle;
import com.smart.agriculture.Do.MessagesList;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Vo.MessagesList.MyCommentMessageListVo;
import com.smart.agriculture.Vo.MessagesList.MyLikeMessageListVo;
import com.smart.agriculture.Vo.MessagesList.MySystemMessageListVo;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.MessagesList.MessageType;
import com.smart.agriculture.mapper.MessagesListMapper;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IMessagesListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            flag.setIsRead(false);
            baseMapper.updateById(flag);
        }
    }

    @Override
    public void sendCommentMessage(String acceptNumber, String otherId, String content, Integer type) {
        String username = isVoidService.isLogin();
        MessagesList messagesList = new MessagesList();
        messagesList.setAcceptNumber(acceptNumber);
        messagesList.setSendNumber(username);
        messagesList.setContent(content);
        messagesList.setType(type);
        messagesList.setOtherId(otherId);
        baseMapper.insert(messagesList);
    }

    @Override
    public CommonResult<List<MySystemMessageListVo>> queryMyMesList() {
        String username = isVoidService.isLogin();
        List<MessagesList> messagesLists = baseMapper.selectList(new QueryWrapper<MessagesList>().lambda()
                .eq(MessagesList::getAcceptNumber,username)
                .eq(MessagesList::getType,MessageType.system.getCode()));
        List<MySystemMessageListVo> vos = new ArrayList<>();
        //系统消息
        for (MessagesList message : messagesLists) {
            MySystemMessageListVo vo = new MySystemMessageListVo();
            BeanUtil.copyProperties(message,vo);
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @Override
    public CommonResult<List<MyLikeMessageListVo>> queryLikeMes() {
        String username = isVoidService.isLogin();
        List<MessagesList> messagesLists = baseMapper.selectList(new QueryWrapper<MessagesList>().lambda()
                .eq(MessagesList::getAcceptNumber,username)
                .eq(MessagesList::getType,MessageType.like.getCode()));
        List<MyLikeMessageListVo> vos = new ArrayList<>();
        //帖子点赞消息
        for (MessagesList message : messagesLists) {
            MyLikeMessageListVo vo = new MyLikeMessageListVo();
            FreedomArticle freedomArticle = isVoidService.freedomArticleIsExist(Long.valueOf(message.getOtherId()));
            BeanUtil.copyProperties(message,vo);
            vo.setData(freedomArticle.getTitle());
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @Override
    public CommonResult<List<MyCommentMessageListVo>> queryCommentMes() {
        String username = isVoidService.isLogin();
        List<Integer> ids = new ArrayList<>();
        ids.add(MessageType.comment.getCode());
        ids.add(MessageType.article.getCode());
        List<MessagesList> messagesLists = baseMapper.selectList(new QueryWrapper<MessagesList>().lambda()
                .eq(MessagesList::getAcceptNumber,username)
                .in(MessagesList::getType,ids));
        List<MyCommentMessageListVo> vos = new ArrayList<>();
        //回复信息包装
        for (MessagesList message : messagesLists) {
            MyCommentMessageListVo vo = new MyCommentMessageListVo();
            SysUser sysUser = isVoidService.userIsExist(message.getSendNumber());
            BeanUtil.copyProperties(message,vo);
            if (Objects.equals(message.getType(), MessageType.article.getCode())) {
                FreedomArticle freedomArticle = isVoidService.freedomArticleIsExist(Long.valueOf(message.getOtherId()));
                vo.setData(freedomArticle.getTitle());
            }if (Objects.equals(message.getType(), MessageType.comment.getCode())) {
                Comment comment = isVoidService.commentIsExist(Long.valueOf(message.getOtherId()));
                vo.setData(comment.getContent());
            }
            //发布者信息包装
            SysUserArticleVo sysUserArticleVo = new SysUserArticleVo();
            sysUserArticleVo.setAuthorUsername(sysUser.getUsername());
            sysUserArticleVo.setAuthorNickname(sysUser.getNickname());
            sysUserArticleVo.setAuthorPicture(sysUser.getHeadPicture());
            vo.setSysUserArticleVo(sysUserArticleVo);
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @Override
    public CommonResult<String> isRead(String id) {
        MessagesList s = baseMapper.selectOneById(id);
        MessagesList messagesList = new MessagesList();
        BeanUtil.copyProperties(s,messagesList);
        messagesList.setIsRead(true);
        int i = baseMapper.updateById(messagesList);
        if (i>0) return CommonResult.success("操作成功！");
        else return CommonResult.failed("未知错误！");
    }

}
