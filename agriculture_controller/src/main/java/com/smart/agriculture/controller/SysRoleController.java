package com.smart.agriculture.controller;


import com.smart.agriculture.Do.SysRole;
import com.smart.agriculture.Dto.SysRole.SysAddUserRoleDto;
import com.smart.agriculture.Dto.SysRole.SysRoleAddDto;
import com.smart.agriculture.Dto.SysRole.SysRoleUpdateDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.ISysRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * cms_role  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Resource
    private ISysRoleService roleService;

    @ApiOperation("新增角色")
    @PostMapping("/addRole")
    public CommonResult<String> addRole(@RequestBody SysRoleAddDto sysRoleAddDto) {
        return roleService.addRole(sysRoleAddDto);
    }

    @ApiOperation("增加用户角色")
    @PutMapping("/addUserRole")
    public CommonResult<String> addUserRole(@RequestBody SysAddUserRoleDto addUserRoleDto) {
        return roleService.addUserRole(addUserRoleDto);
    }

    @ApiOperation("删除角色(可批量)")
    @DeleteMapping()
    public CommonResult<String> deletesRole( @RequestBody List<Long> ids) {
        boolean result = roleService.removeByIds(ids);
        if (result) return CommonResult.success("删除角色成功");
        return CommonResult.failed("删除角色失败");
    }

    @ApiOperation("更新角色")
    @PutMapping("/UpdateRole")
    public CommonResult<String> updateRole(@RequestBody SysRoleUpdateDto sysRoleUpdateDto) {
        return roleService.updateRole(sysRoleUpdateDto);
    }

    @ApiOperation("查询所有角色")
    @GetMapping("/all")
    public CommonResult<List<SysRole>> searchRole() {
        return roleService.selectAllRole();
    }

}

