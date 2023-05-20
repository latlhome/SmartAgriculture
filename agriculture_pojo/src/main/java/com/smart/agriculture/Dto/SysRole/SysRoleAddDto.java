package com.smart.agriculture.Dto.SysRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author  权限脚色添加
 */
@Data
@ApiModel("SysRoleAddDto :: 角色添加")
public class SysRoleAddDto {

    /**
     * 角色中文名称 角色中文名称
     */
    @ApiModelProperty("角色中文名称")
    @NotBlank(message = "角色名称不为空")
    private String name;

    /**
     * 角色对应的Number,不为空不重复
     */
    @ApiModelProperty("角色对应的Number 不为空-不重复")
    @NotBlank(message = "角色对应的Number不为空")
    private String typeNumber;

}
