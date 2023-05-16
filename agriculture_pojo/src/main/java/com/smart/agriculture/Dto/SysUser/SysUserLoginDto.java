package com.smart.agriculture.Dto.SysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: jiangzhuohang
 * @Date: 2021/08/14/10:11
 * @Description:
 */
@Data
@ApiModel("SysAdminLoginDto :: 用户登录")
public class SysUserLoginDto {

    /**
     * 帐号
     */
    @ApiModelProperty("帐号")
    @NotBlank(message = "帐号不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
