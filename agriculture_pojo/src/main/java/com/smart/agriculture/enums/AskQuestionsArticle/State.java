package com.smart.agriculture.enums.AskQuestionsArticle;

public enum State {
    unresolved("未解决",-1),
    unprocessed("待处理",0),
    resolved("已解决",1)
    ;

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    private final String type;
    private final Integer code;

    State(String type, Integer code) {
        this.type = type;
        this.code = code;
    }
}
