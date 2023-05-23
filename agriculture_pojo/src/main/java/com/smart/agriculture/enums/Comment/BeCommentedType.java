package com.smart.agriculture.enums.Comment;

public enum BeCommentedType {
    COMMENTED_TYPE("评论",2),ARTICLE_TYPE("文章",1);

    BeCommentedType(String type, Integer code) {
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
