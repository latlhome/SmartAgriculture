package com.smart.agriculture.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Bo.AdminAuthData;
import com.smart.agriculture.Do.SysPermission;
import com.smart.agriculture.Do.SysRole;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.enums.SysPremission.PermissionType;
import com.smart.agriculture.mapper.SysPermissionMapper;
import com.smart.agriculture.mapper.SysRoleMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * SysPermissionServiceImpl  服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysPermissionMapper permissionMapper;

    @Override
    public List<String> getPermission(String number, String type) {
        List<String> result = new ArrayList<>();
        // 查询用户权限值
        SysUser cmsAdmin = sysUserMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, number));
        if (cmsAdmin == null|| ObjectUtil.isEmpty(cmsAdmin.getAuthData())) {
            return new ArrayList<>();
        }
        // 字符串生成对象
        AdminAuthData sysUserAuthData = JSONUtil.toBean(cmsAdmin.getAuthData(), AdminAuthData.class);
        AdminAuthData permissionData = new AdminAuthData(); // 结果权限
        // 角色列表
        List<String> roleList = sysUserAuthData.getAuthList();
        if (roleList.size() != 0) {
            //获取角色权限
            List<SysRole> authRoleList = roleMapper.selectList(new QueryWrapper<SysRole>().lambda()
                    .select(SysRole::getAuthData)
                    .in(SysRole::getId, roleList));    //拿到角色中的权限值并保存
            List<String> result1 = new ArrayList<>();
            for (SysRole sysUserRole : authRoleList) {
                if (ObjectUtil.isEmpty(sysUserRole)){
                    break;
                }
                AdminAuthData roleAuth = JSONUtil.toBean(sysUserRole.getAuthData(), AdminAuthData.class);
                if (roleAuth.getAuthList().size()!=0) {
                    roleAuth.getAuthList().stream().forEach(
                            roleAuth1->{
                                result1.add(roleAuth1); //整合角色中的权限id值
                            }
                    );
                }
            }
            permissionData.setAuthList(result1); //取得权限值
        }
        List<String> resultPermissionList = new ArrayList<>();
        if(permissionData.getAuthList()!=null){
            resultPermissionList = permissionData.getAuthList().stream().distinct().collect(Collectors.toList()); // 去重
        }
        // 查出权限列表
        if (resultPermissionList.size() != 0) {
            List<SysPermission> sysPermissions = permissionMapper.selectList(new QueryWrapper<SysPermission>().lambda()
                    .in(SysPermission::getId, resultPermissionList));
            for (SysPermission sy : sysPermissions) {
                if (sy.getType().equals(PermissionType.BUTTON.getCode())) {
                    result.add(sy.getValue());
                }
            }
        }
        if(type.equals("0")){
            return result;
        }else if(type.equals("1")){//权限个数
            List<String> buttonCount = new ArrayList<>();
            buttonCount.add(ObjectUtil.toString(result.size()));
            return buttonCount;
        }
        return result;
    }
}
