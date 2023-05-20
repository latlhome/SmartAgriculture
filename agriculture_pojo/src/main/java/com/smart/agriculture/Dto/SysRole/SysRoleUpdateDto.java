package com.smart.agriculture.Dto.SysRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 更新权限
 */
@Data
@ApiModel("SysRoleUpdateDto :: 角色更新")
public class SysRoleUpdateDto {

    /**
     *角色id
     */
    @ApiModelProperty("角色id")
    @NotBlank(message = "角色id不能为空")
    private Long id;

    /**
     * 角色中文名称 角色中文名称
     */
    @ApiModelProperty("角色中文名")
    private String name;


    /**
     * 权限码 权限编码
     */
    @ApiModelProperty("角色权限")
    private List<String> authData;

}
