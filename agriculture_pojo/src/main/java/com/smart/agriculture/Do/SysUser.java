package com.smart.agriculture.Do;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 用户编号
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份类型
     */
    private Integer type;


    /**
     * 0默认1男2女3保密
     */
    private Integer sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限集合
     */
    private String authData;

}
