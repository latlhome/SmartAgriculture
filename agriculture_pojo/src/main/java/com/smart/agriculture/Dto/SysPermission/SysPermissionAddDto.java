package com.smart.agriculture.Dto.SysPermission;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("SysPermissionAdd :: 权限添加")
public class SysPermissionAddDto {

    /**
     * 父级权限ID 父级权限ID
     */
    @NotNull(message = "父权限ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty("父权限ID")
    private Long parentId;


    /**
     * 权限编码 权限编码
     */
    @ApiModelProperty("权限编码")
    private String value;

    /**
     * 权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @NotNull(message = "权限类型不能为空 0->目录；1->菜单；2->按钮")
    @ApiModelProperty("权限类型不能为空 0->目录；1->菜单；2->按钮")
    private Integer type;

    /**
     * 前端路由路径
     */
    @ApiModelProperty("前端路由路径")
    private String path;

    /**
     * 权限中文名称
     */
    @NotBlank(message = "权限中文名称不能为空")
    @ApiModelProperty("权限中文名称")
    private String label;

    /**
     * 组件路径
     */
    @ApiModelProperty("组件路径")
    private String component;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;


    /**
     * 是否被禁用
     */
    @ApiModelProperty("是否被禁用")
    private Boolean ban;


    /**
     * 菜单排序
     */
    @ApiModelProperty("菜单排序")
    private Integer sort;



}
