package com.smart.agriculture.Dto.SysPermission;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@ApiModel("SysPermissionUpdate :: 权限更新")
public class SysPermissionUpdateDto {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @NotNull(message = "权限ID不能为空")
    @ApiModelProperty("权限ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 父级权限ID 父级权限ID
     */
    @ApiModelProperty("父级权限ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;



    /**
     * 权限编码 权限编码
     */
    @ApiModelProperty("权限编码")
    private String value;

    /**
     * 权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    @ApiModelProperty("权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    private Integer type;

    /**
     * 前端路由路径 前端路由路径
     */
    @ApiModelProperty("前端路由路径")
    private String path;


    /**
     * 路由名称
     */
    @ApiModelProperty("路由名称")
    private String label;

    /**
     * 组件路由
     */
    @ApiModelProperty("组件路由")
    private String component;

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

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;



}
