package com.smart.agriculture.enums.Comment;

public enum BeCmmentedType {
    COMMENTED_TYPE("评论",2),ARTICLE_TYPE("文章",1);

    BeCmmentedType(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    private String type;
    private Integer code;
}
