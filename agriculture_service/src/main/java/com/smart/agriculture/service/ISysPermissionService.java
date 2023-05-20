package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.SysPermission;
import com.smart.agriculture.Dto.SysPermission.*;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.SysPermission.SysMenuVo;
import com.smart.agriculture.Vo.SysPermission.SysTreePermissionVo;
import com.smart.agriculture.common.result.CommonResult;

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
    /**+
     * 获取用户所携带的按钮信息
     * @param number 用户username
     * @param s 0代表权限数据 1是代表返回权限个数
     * @return 用户所携带的按钮信息
     */

    List<String> getPermission(String number, String s);

    /**
     * 清除 token
     * @param b 立即退出
     * @param id 用户ID
     */
    void clearToken(boolean b, Long id);

    /**
     * 更新权限
     * @param sysUserPermissionUpdateDto 更新or 新增 Dto
     * @return 操作状态
     */

    CommonResult<String> updateUserPermission(SysUserPermissionUpdateDto sysUserPermissionUpdateDto);

    /**
     * 查询权限列表
     * @param dto 分页Dto
     * @return  分页后的权限数据
     */
    CommonResult<PageVo<SysPermission>> selectPermissionList(SelectPermissionListDto dto);

    /**
     * 查询用户树状结构的菜单
     * @param username 用户名
     * @return 用户树状结构的菜单
     */
    CommonResult<List<SysMenuVo>> sysTreePermissions(String username);

    /**
     * 删除权限
     * @param dto 权限ID
     * @return 操作状态
     */

    CommonResult<String> removePermission(SysPermissionBanRestartDto dto);

    /**
     * 查询角色id对应权限
     * @param id 角色ID
     * @return 对应的权限
     */

    CommonResult<List<String>> getByRolePermission(Long id);

    /**
     * 获取全部权限 --树状图
     * @return 全部权限树状图
     */

    CommonResult<List<SysTreePermissionVo>> treePermission();

    /**
     * 查询所有权限不分页
     * @return 所有权限
     */
    CommonResult<List<SysPermission>> selectAllPermission();

    /**
     * 添加权限
     * @param dto 添加权限Dto
     * @return 操作状态
     */
    CommonResult<String> addPermission(SysPermissionAddDto dto);

    /**
     * 更新全西安
     * @param dto 更新权限Dto
     * @return 操作状态
     */
    CommonResult<String> updatePermission(SysPermissionUpdateDto dto);
}
