package com.smart.agriculture.enums.SysUser;

public enum UserType {
    developer("开发者",0),
    ordinaryUser("普通用户",1),
    expert("专家",2);
    private String type;
    private Integer code;

    UserType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    UserType(String type, Integer code) {
        this.type = type;
        this.code = code;
    }
}
