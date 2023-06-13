package com.smart.agriculture.Vo.SysUser;

import lombok.Data;

@Data
public class SysUserVo {
    /**
     * 用户编号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像地址
     */
    private String headPicture;
    /**
     * 身份类型 0管理员 1普通用户 2专家
     */
    private String userType;


    /**
     * 0保密1男2女
     */
    private String sex;

    /**
     * 电话号码
     */
    private String phone;

}
