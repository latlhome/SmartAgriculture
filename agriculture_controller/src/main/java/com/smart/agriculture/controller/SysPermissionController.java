package com.smart.agriculture.controller;


import com.smart.agriculture.Do.SysPermission;
import com.smart.agriculture.Dto.SysPermission.*;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.SysPermission.SysMenuVo;
import com.smart.agriculture.Vo.SysPermission.SysTreePermissionVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.ISysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 0：系统 权限管理接口
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/sysPermission")
@Api(tags="0==系统 权限管理接口")
public class SysPermissionController {
    @Resource
    private ISysPermissionService permissionService;


    /**
     *  更新用户权限
     * @param sysUserPermissionUpdateDto 更新 or 新增 Dto
     * @return 操作状态
     */
    @ApiOperation("更新用户权限用户端user:permission:update")
    @PutMapping("/update/permission")
    public CommonResult<String> updatePermission(@Validated @RequestBody SysUserPermissionUpdateDto sysUserPermissionUpdateDto) {
      return permissionService.updateUserPermission(sysUserPermissionUpdateDto);
    }

    /**
     * 查询权限List
     * @param dto 分页Dto
     * @return 分页后的用户权限List
     */
    @ApiOperation("查询权限permission:list")
    @GetMapping("/list")
    public CommonResult<PageVo<SysPermission>> list(SelectPermissionListDto dto) {
        return permissionService.selectPermissionList(dto);
    }


    /**
     * 查询用户菜单(树形结构）
     * @return 树状结构的用户菜单
     */
    @ApiOperation("查询菜单(树形结构）permission:tree")
    @GetMapping("/menu/{username}")
    public CommonResult<List<SysMenuVo>> menu(@PathVariable String  username) {
        return permissionService.sysTreePermissions(username);
    }

    @ApiOperation("查询角色id对应权限")
    @GetMapping("/{id}")
    public CommonResult<List<String>> searchRole(@PathVariable Long id) {
        return permissionService.getByRolePermission(id);
    }


    /**
     * 查询全部权限菜单(树形结构）
     * @return  全部权限菜单(树形结构）
     */
    @ApiOperation("查询全部权限菜单(树形结构）permission:tree")
    @GetMapping()
    public CommonResult<List<SysTreePermissionVo>> tree() {
        return  permissionService.treePermission();
    }


    /**
     * 删除权限
     * @param dto 权限ID
     * @return 操作状态
     */
    @ApiOperation("删除权限permission:delete")
    @DeleteMapping()
    public CommonResult<String> delete(@RequestBody @Validated SysPermissionBanRestartDto dto){
        return permissionService.removePermission(dto);
    }

    /**
     * 查询所有权限不分页
     * @return 全部权限
     */
    @ApiOperation("查询所有权限,平面结构permission:all")
    @GetMapping("/all")
    public CommonResult<List<SysPermission>> all() {
        return permissionService.selectAllPermission();
    }

    /**
     * 更新权限
     * @param dto 更新权限使用
     * @return 操作状态
     */
    @ApiOperation("更新权限，permission:update")
    @PutMapping()
    public CommonResult<String> update(@RequestBody @Validated SysPermissionUpdateDto dto){
        return permissionService.updatePermission(dto);
    }

    /**
     * 添加权限
     * @param dto Add权限Dto
     * @return 操作状态
     */
    @ApiOperation("添加权限permission:add")
    @PostMapping()
    public CommonResult<String> addPermission(@RequestBody @Validated SysPermissionAddDto dto) {
        return permissionService.addPermission(dto);
    }

}

