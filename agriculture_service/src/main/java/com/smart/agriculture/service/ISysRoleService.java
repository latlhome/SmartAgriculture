package com.smart.agriculture.service;

import com.smart.agriculture.Do.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.SysRole.SysAddUserRoleDto;
import com.smart.agriculture.Dto.SysRole.SysRoleAddDto;
import com.smart.agriculture.Dto.SysRole.SysRoleUpdateDto;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 * cms_role  服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 新增权限下角色
     * @param sysRoleAddDto 新增角色Dto
     * @return 操作状态
     */

    CommonResult<String> addRole(SysRoleAddDto sysRoleAddDto);

    /**
     * 给User增加角色
     * @param addUserRoleDto User增加角色Dto
     * @return 操作状态
     */

    CommonResult<String> addUserRole(SysAddUserRoleDto addUserRoleDto);

    /**
     * 更新角色信息
     * @param sysRoleUpdateDto 更新角色信息Dto
     * @return 操作状态
     */

    CommonResult<String> updateRole(SysRoleUpdateDto sysRoleUpdateDto);

    /**
     * 查询所有角色
     * @return 所有角色
     */

    CommonResult<List<SysRole>> selectAllRole();
}
