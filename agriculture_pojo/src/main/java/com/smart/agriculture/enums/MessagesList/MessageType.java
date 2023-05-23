package com.smart.agriculture.enums.MessagesList;

public enum MessageType {
    system("系统通知",10),
    like("点赞通知",20),
    article("帖子回复",30),
    comment("评论回复",40);

     MessageType(String type, Integer code) {
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
