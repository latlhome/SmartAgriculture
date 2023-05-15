package com.smart.agriculture.Do;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseDo implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    private String username;

    /**
     * 真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    private String name;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户头像地址
     */
    @ApiModelProperty("用户头像地址")

    private String headPicture;
    /**
     * 身份类型
     */
    @ApiModelProperty("用户类型")
    private Integer userType;


    /**
     * 0保密1男2女
     */
    @ApiModelProperty("性别 0保密1男2女")
    private Integer sex;

    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String phone;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 权限集合
     */
    @ApiModelProperty("权限集合")
    private String authData;
}
