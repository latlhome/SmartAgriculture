package com.smart.agriculture.enums.SysPremission;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ylx
 */
public enum  PermissionType {
    /**
     * 权限类型
     */
    BUTTON(2, "按钮"), MENU(1, "菜单");
    private String type;
    private Integer code;

    PermissionType(Integer code, String type) {
        this.code = code;
        this.type = type;
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
}
