package com.smart.agriculture.enums.AskQuestionsArticleFlow;

public enum Adopt {

    NotAdopted("未采纳",-1),
    unprocessed("待处理",0),
    Adopt("已采纳",1);

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    Adopt(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    private final String type;
    private final Integer code;
}
