package com.smart.agriculture.enums.SysUser;

public enum userSex {
    secret("保密",0),
    men("男",1),
    women("女",2)
    ;
    private String type;
    private Integer code;

    userSex(String type, Integer code) {
        this.type = type;
        this.code = code;
    }
}
