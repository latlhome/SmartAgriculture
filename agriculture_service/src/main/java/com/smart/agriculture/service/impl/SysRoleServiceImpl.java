package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Bo.AdminAuthData;
import com.smart.agriculture.Do.BaseDo;
import com.smart.agriculture.Do.SysRole;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.SysRole.SysAddUserRoleDto;
import com.smart.agriculture.Dto.SysRole.SysRoleAddDto;
import com.smart.agriculture.Dto.SysRole.SysRoleUpdateDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.mapper.SysRoleMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.ISysPermissionService;
import com.smart.agriculture.service.ISysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * cms_role  服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private ISysPermissionService permissionService;
    @Resource
    private SysUserMapper userMapper;

    @Override
    public CommonResult<String> addRole(SysRoleAddDto sysRoleAddDto) {
        SysRole sysRole = baseMapper.selectOne(new QueryWrapper<SysRole>().lambda()
                .eq(SysRole::getName, sysRoleAddDto.getName()));
        if (ObjectUtil.isNotNull(sysRole))  return CommonResult.failed("新增失败,该角色名已存在！");
        SysRole typeNumber = baseMapper.selectOne(new QueryWrapper<SysRole>().lambda()
                .eq(SysRole::getTypeNumber, sysRoleAddDto.getTypeNumber()));
        if (ObjectUtil.isNotNull(typeNumber))  return CommonResult.failed("新增失败,该TypeNumber已经被使用！");
        SysRole flag = new SysRole();
        BeanUtil.copyProperties(sysRoleAddDto, flag);
        int insert = baseMapper.insert(flag);
        if (insert>0) return CommonResult.success("新增成功！");
        return CommonResult.failed("未知错误！");
    }

    @Override
    public CommonResult<String> addUserRole(SysAddUserRoleDto addUserRoleDto) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getUsername, addUserRoleDto.getUsername())); //根据编号查询管理员
        if (ObjectUtil.isNotNull(user)) {
            AdminAuthData admin = JSONUtil.toBean(user.getAuthData(), AdminAuthData.class);
            if (!addUserRoleDto.getAuthData().isEmpty() && !Objects.equals(addUserRoleDto.getAuthData().get(0), "")){
                List<AdminAuthData> authDataList = new ArrayList<>();
                AdminAuthData data = new AdminAuthData();
                for (String authDatum : addUserRoleDto.getAuthData()) {
                    //获取角色权限
                    SysRole sysRole = baseMapper.selectOne(new QueryWrapper<SysRole>().lambda()
                            .eq(SysRole::getName, authDatum));
                    //转换成对象
                    AdminAuthData adminAuthData = JSONUtil.toBean(sysRole.getAuthData(), AdminAuthData.class);
                    adminAuthData.setAuthList(Convert.toList(String.class, sysRole.getId()));
                    authDataList.add(adminAuthData);
                }
                for (AdminAuthData adminAuthData : authDataList) {
                    //将两个List合并添加
                    List<String> auth = (List<String>) CollUtil.union(data.getAuthList(), adminAuthData.getAuthList());
                    data.setAuthList(auth);
                    List<String> role = (List<String>) CollUtil.union(data.getAuthList(), adminAuthData.getAuthList());
                    data.setAuthList(role);
                }
                //去重
                admin.setAuthList(data.getAuthList().stream().distinct().collect(Collectors.toList()));
                user.setAuthData(JSONUtil.toJsonStr(admin));
            } else {
                //如果没有角色，那就没有权限
                admin.getAuthList().clear();
                user.setAuthData(JSONUtil.toJsonStr(admin));
            }
            // 清除token
            if (userMapper.updateById(user) == 1) {
                permissionService.clearToken(true, user.getId());
                return CommonResult.success("操作成功！");
            }
        }
        return CommonResult.failed("操作未知错误！");
    }

    @Override
    public CommonResult<String> updateRole(SysRoleUpdateDto sysRoleUpdateDto) {
        SysRole sysRole = baseMapper.selectOne(new QueryWrapper<SysRole>().lambda()
                .eq(SysRole::getId, sysRoleUpdateDto.getId()));
        if (ObjectUtil.isNull(sysRole)) return CommonResult.failed("角色不存在！");
        AdminAuthData adminAuthData = new AdminAuthData(); //权限合集
        List<String> collect = sysRoleUpdateDto.getAuthData().stream().distinct()
                        .collect(Collectors.toList());//stream去重，生成新的list合集
        adminAuthData.setAuthList(collect);
        sysRole.setAuthData(JSONUtil.toJsonStr(adminAuthData));

        if (StringUtils.isNoneBlank(sysRoleUpdateDto.getName())) { //更改名称
            sysRole.setName(sysRoleUpdateDto.getName());
        }
        int update = baseMapper.updateById(sysRole);
        if (update>0) return CommonResult.success("更新成功！");
        return CommonResult.failed("更新未知错误!");

    }

    @Override
    public CommonResult<List<SysRole>> selectAllRole() {
       return CommonResult.success(baseMapper.selectList(new QueryWrapper<SysRole>().lambda().orderByAsc(BaseDo::getCreateTime)));
    }
}
