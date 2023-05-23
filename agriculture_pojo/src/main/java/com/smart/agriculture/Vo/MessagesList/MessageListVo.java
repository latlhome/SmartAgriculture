package com.smart.agriculture.Vo.MessagesList;

import lombok.Data;

import java.util.List;
@Data
public class MessageListVo<T> {
    private List<T> message;
}
