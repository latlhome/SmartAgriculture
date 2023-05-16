package com.smart.agriculture.security.pojo.security;

import com.smart.agriculture.security.pojo.premission.SysPermission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RedisUserInfo implements Serializable {


    /**
     * token
     */
    private String token;
    /**
     * 用户number
     */
    private String username;

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
    private Integer userType;

    /**
     * 菜单集合
     */
    private List<SysTreePermissionVo> menuList;


}