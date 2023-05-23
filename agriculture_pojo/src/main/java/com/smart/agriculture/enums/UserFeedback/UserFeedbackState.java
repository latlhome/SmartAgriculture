package com.smart.agriculture.enums.UserFeedback;

public enum UserFeedbackState {
    unprocessed("未处理",-1),
    inProcess("处理中",0),
    resolved("已解决",1);

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    private final String type;
    private final Integer code;

    UserFeedbackState(String type, Integer code) {
        this.type = type;
        this.code = code;
    }
}
