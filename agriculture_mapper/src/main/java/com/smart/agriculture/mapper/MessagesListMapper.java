package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.MessagesList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-23
 */
public interface MessagesListMapper extends BaseMapper<MessagesList> {
    /**
     * 查询单个消息
     * @param id 消息id
     * @return 消息
     */
    MessagesList selectOneById(String id);
}
