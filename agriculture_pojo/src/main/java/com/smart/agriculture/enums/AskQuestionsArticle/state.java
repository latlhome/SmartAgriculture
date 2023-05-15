package com.smart.agriculture.enums.AskQuestionsArticle;

public enum state {
    unresolved("未解决",-1),
    unprocessed("待处理",0),
    resolved("已解决",1)
    ;
    private String type;
    private Integer code;

    state(String type, Integer code) {
        this.type = type;
        this.code = code;
    }
}
