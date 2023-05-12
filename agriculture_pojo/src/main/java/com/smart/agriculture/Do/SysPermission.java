package com.smart.agriculture.Do;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysPermission extends BaseDo {

    private static final long serialVersionUID = 1L;

    /**
     * 父级权限ID 父级权限ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 权限编码 权限编码
     */
    private String value;

    /**
     * 权限类型 权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）
     */
    private Integer type;

    /**
     * 前端路由路径 前端路由路径
     */
    private String path;


    /**
     * 组件路由
     */
    private String component;

    /**
     * 是否被禁用
     */
    private Boolean ban;

    /**
     * 菜单排序
     */
    private Integer sort;


    /**
     * 图标
     */
    private String icon;

    /**
     * 路由名称
     */
    private String label;

}
