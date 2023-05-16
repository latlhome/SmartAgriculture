package com.smart.agriculture.service;

import com.smart.agriculture.Do.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * ISysPermissionService  服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface ISysPermissionService extends IService<SysPermission> {

    List<String> getPermission(String number, String s);
}
