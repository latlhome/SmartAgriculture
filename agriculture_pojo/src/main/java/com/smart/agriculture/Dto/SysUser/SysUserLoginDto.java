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
    @ApiModelProperty(value = "用户名(4-50个字符)", example = "root", required = true, position = 1)
    @NotBlank(message = "帐号不能为空")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码(不小于6个字符)", example = "666666", required = true, position = 2)
    @NotBlank(message = "密码不能为空")
    private String password;

}
