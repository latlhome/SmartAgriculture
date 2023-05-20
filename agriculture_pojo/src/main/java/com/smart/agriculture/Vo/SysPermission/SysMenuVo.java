package com.smart.agriculture.Vo.SysPermission;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * 用户树状结构的菜单
 */
@Data
public class SysMenuVo {
    private Integer sort;
    private String label;
    private String path;
    private String icon;
    private String component;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer type;
    private List<SysMenuVo> children;
}
