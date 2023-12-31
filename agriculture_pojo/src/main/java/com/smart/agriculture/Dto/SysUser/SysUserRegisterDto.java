package com.smart.agriculture.Dto.SysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 */
@Data
@ApiModel("CmsUserRegisterDto :: 用户注册")
public class SysUserRegisterDto {
    /**
     * 账号
     */
    @ApiModelProperty("帐号")
    @NotBlank(message = "帐号不能为空")
    private String username;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;


    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Integer sex;


    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;


}

