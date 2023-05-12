package com.smart.agriculture.security.pojo.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.smart.agriculture.Do.SysPermission;
import com.smart.agriculture.Vo.SysTreePermissionVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RedisUserInfo implements Serializable {

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户number
     */
    private String Number;

    /**
     * 用户name
     */
    private String Name;

    /**
     * 用户nickname
     */
    private String Nickname;

    /**
     * token
     */
    private String token;

    /**
     * 权限列表
     */
    private List<SysPermission> permissionList;

    /**
     * 按钮列表
     */
    private List<String> permissionValueList;


    /**
     * 类型
     */
    private Integer type;

    /**
     * 菜单集合
     */
    private List<SysTreePermissionVo> menuList;


}