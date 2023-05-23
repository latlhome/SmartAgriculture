package com.smart.agriculture.enums.MessagesList;

public enum MessageIsRead {
    unread("未读",0),
    read("已读",1);

    MessageIsRead(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }


    public Integer getCode() {
        return code;
    }


    private final String type;
    private final Integer code;
}
