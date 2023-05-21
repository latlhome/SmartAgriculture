package com.smart.agriculture.enums.UserApply;

public enum IsAgree {
    refunded("退回修改",-2),
    refuse("拒绝",-1),
    await("待审核",0),
    agree("同意",1);

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    IsAgree(String type, Integer code) {
        this.type = type;
        this.code = code;
    }

    private final String type;
    private final Integer code;
}
