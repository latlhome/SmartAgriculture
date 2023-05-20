package com.smart.agriculture.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Bo.AdminAuthData;
import com.smart.agriculture.Do.SysPermission;
import com.smart.agriculture.Do.SysRole;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.SysPermission.*;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.SysPermission.SysMenuVo;
import com.smart.agriculture.Vo.SysPermission.SysTreePermissionVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.SysPremission.PermissionType;
import com.smart.agriculture.mapper.SysPermissionMapper;
import com.smart.agriculture.mapper.SysRoleMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.security.service.impl.IRedisServiceImpl;
import com.smart.agriculture.service.ISysPermissionService;
import org.springframework.beans.BeanUtils;
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
    private IRedisServiceImpl redisService;
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
                    //整合角色中的权限id值
                    result1.addAll(roleAuth.getAuthList());
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

    @Override
    public void clearToken(boolean b, Long id) {
        // 判断是否马上登出
        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda()
                .select(SysUser::getUsername)
                .eq(SysUser::getId, id));
        if (b && user != null) {
            redisService.remove(user.getUsername());
        } else if (!b && user != null) {
            redisService.expire(user.getUsername(), 60 * 60);
        }
    }

    @Override
    public CommonResult<String> updateUserPermission(SysUserPermissionUpdateDto sysUserPermissionUpdateDto) {
        // 去重
        sysUserPermissionUpdateDto.setAuthList(sysUserPermissionUpdateDto.getAuthList().stream().distinct().collect(Collectors.toList()));
        SysUser sysUser = sysUserMapper.selectOneByUsername(sysUserPermissionUpdateDto.getUsername());
        if (sysUser == null) {
            return CommonResult.failed("用户不存在！");
        }
        // 拿出原本的角色 + 权限 字符串
        String authStr = sysUser.getAuthData();
        // 把字符串转对象
        AdminAuthData sysUserAuthData = JSONUtil.toBean(authStr, AdminAuthData.class);
        // 把新的权限列表设置到对象中
        sysUserAuthData.setAuthList(sysUserPermissionUpdateDto.getAuthList());
        // 把对象转化成json设置到对象中
        sysUser.setAuthData(JSONUtil.toJsonStr(sysUserAuthData));
        // 清除token
        if (sysUserMapper.updateById(sysUser) == 1) {
            clearToken(sysUserPermissionUpdateDto.getIfFast(),sysUser.getId());
            return CommonResult.success("操作成功！");
        }
        return CommonResult.failed("操作失败！未知错误！");
    }

    @Override
    public CommonResult<PageVo<SysPermission>> selectPermissionList(SelectPermissionListDto dto) {
        IPage<SysPermission> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        IPage<SysPermission> sysPermissionIPage = baseMapper.selectPage(page, new QueryWrapper<SysPermission>().lambda()
                .like(SysPermission::getLabel, dto.getLabel())
                .orderByDesc(SysPermission::getCreateTime));
        PageVo<SysPermission> vo = new PageVo<>();
        vo.setData(sysPermissionIPage.getRecords());
        vo.setTotalSize(sysPermissionIPage.getTotal());
        vo.setPageSize(sysPermissionIPage.getSize());
        vo.setPageNum(sysPermissionIPage.getCurrent());
        vo.setTotalPages(sysPermissionIPage.getPages());
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult<List<SysMenuVo>> sysTreePermissions(String username) {
        // 查询用户权限值
        SysUser cmsAdmin = sysUserMapper.selectOne(new QueryWrapper<SysUser>()
                .lambda()
                .eq(SysUser::getUsername, username));
        if (cmsAdmin == null) {
            return CommonResult.success(new ArrayList<>());
        }
        // 字符串生成对象
        AdminAuthData sysUserAuthData = JSONUtil.toBean(cmsAdmin.getAuthData(), AdminAuthData.class);
        // 权限列表
        AdminAuthData permissionData = new AdminAuthData(); // 结果权限

        // 角色列表
        List<String> roleList = sysUserAuthData.getAuthList();
        // 查询角色 拿到角色表中的权限值
        if (roleList.size() != 0) {
            //获取角色权限
            List<SysRole> authRoleList = roleMapper.selectList(new QueryWrapper<SysRole>().lambda()
                    .select(SysRole::getAuthData)
                    .in(SysRole::getId, roleList));    //拿到角色中的权限值并保存
            List<String> result1 = new ArrayList<>();
            for (SysRole sysUserRole : authRoleList) {
                AdminAuthData roleAuth = JSONUtil.toBean(sysUserRole.getAuthData(), AdminAuthData.class);
                if (roleAuth.getAuthList().size()!=0) {
                    //整合角色中的权限id值
                    result1.addAll(roleAuth.getAuthList());
                }
            }
            permissionData.setAuthList(result1); //取得权限值
        }
        // 去重
        List<String> resultPermissionList = permissionData.getAuthList().stream().distinct().collect(Collectors.toList()); //结果权限列表
        // 查出权限列表
        if (resultPermissionList.size() != 0) {
            List<SysPermission> sysPermissions = permissionMapper.selectList(new QueryWrapper<SysPermission>().lambda().in(SysPermission::getId, resultPermissionList)); //权限列表
            List<SysMenuVo> sysTreePermissionList = new ArrayList<>();
            List<SysMenuVo> originData = new ArrayList<>();
            for (SysPermission sysPermission : sysPermissions) {//遍历菜单权限
                if (!sysPermission.getType().equals(PermissionType.BUTTON.getCode())) {
                    SysMenuVo sysTreePermissionVo = new SysMenuVo();
                    BeanUtils.copyProperties(sysPermission, sysTreePermissionVo);
                    sysTreePermissionVo.setLabel(sysPermission.getLabel());
                    originData.add(sysTreePermissionVo);
                }
            }
            originData.sort((o1, o2) -> o2.getSort() - o1.getSort());
            for (int i = 0; i < originData.size(); i++) {
                if (originData.get(i).getParentId() == 0) {
                    sysTreePermissionList.add(originData.get(i));
                    continue;
                }
                for (SysMenuVo originDatum : originData) {
                    if (originData.get(i).getParentId().equals(originDatum.getId())) {
                        if (originDatum.getChildren() == null) {
                            originDatum.setChildren(new ArrayList<>());
                        }
                        originDatum.getChildren().add(originData.get(i));
                    }
                }
            }
            return CommonResult.success(sysTreePermissionList);
        }
        return CommonResult.failed("未知错误！");
    }

    @Override
    public CommonResult<String> removePermission(SysPermissionBanRestartDto dto) {
        int i = baseMapper.deleteBatchIds(dto.getPermissionId());
        if (i == dto.getPermissionId().size()) return CommonResult.success("删除成功！");
        return CommonResult.failed("删除未知错误");
    }

    @Override
    public CommonResult<List<String>> getByRolePermission(Long id) {
        SysRole sysRole = roleMapper.selectPermission(id);
        SysRoleChange sysRoleChange = JSONUtil.toBean(sysRole.getAuthData(), SysRoleChange.class);
        List<String> resultId = new ArrayList<>();
        List<SysPermission> permissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().lambda()
                .in(SysPermission::getId, sysRoleChange.getAuthList())); //通过角色权限id   获取权限列表
        List<SysTreePermissionVo> permissionTreeVoList = getPermissionTree(permissionList); //生成树状结构
        getBelow(permissionTreeVoList, resultId);
        return CommonResult.success(resultId);
    }

    @Override
    public CommonResult<List<SysTreePermissionVo>> treePermission() {
        List<SysPermission> permissionList = baseMapper.selectList(new QueryWrapper<SysPermission>().lambda()
                .orderByDesc(SysPermission::getSort)); //得到所有的权限
        List<SysTreePermissionVo> treeVoList = getPermissionTree(permissionList);
        return CommonResult.success(treeVoList);
    }

    @Override
    public CommonResult<List<SysPermission>> selectAllPermission() {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByAsc(SysPermission::getCreateTime);
        List<SysPermission> sysPermissions = baseMapper.selectList(queryWrapper);
        return CommonResult.success(sysPermissions);
    }

    @Override
    public CommonResult<String> addPermission(SysPermissionAddDto dto) {
        if (baseMapper.selectOne(new QueryWrapper<SysPermission>().lambda()
                .eq(SysPermission::getLabel, dto.getLabel())) != null) { //查重
            return CommonResult.failed("名称重复，权限已存在");
        }
        SysPermission Permission = new SysPermission();
        BeanUtils.copyProperties(dto, Permission);
        int insert = baseMapper.insert(Permission);//插入权限
        if (insert > 0) {
            SysPermission sysPermission = baseMapper.selectOne(new QueryWrapper<SysPermission>().lambda()
                    .eq(SysPermission::getLabel, dto.getLabel()));
            String id = sysPermission.getId().toString();  //得到新增权限的id
            SysRole admin = roleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getName, "管理员"));
            String authData = admin.getAuthData();
            AdminAuthData adminAuthData = JSONUtil.toBean(authData, AdminAuthData.class);//原来的权限
            List<String> authList = adminAuthData.getAuthList();
            authList.add(id);
            adminAuthData.setAuthList(authList);//加入新的权限
            admin.setAuthData(JSONUtil.toJsonStr(adminAuthData));
            int i = roleMapper.updateById(admin);
            if (i > 0) {
                return CommonResult.success("新增权限成功！");
            }
            return CommonResult.failed("新增权限成功，动态添加菜单失败！");
        }
        return CommonResult.failed("新增失败！");
    }

    @Override
    public CommonResult<String> updatePermission(SysPermissionUpdateDto dto) {
        SysPermission sysPermission = new SysPermission();
        BeanUtils.copyProperties(dto, sysPermission);
        int i = baseMapper.updateById(sysPermission);
        if (i>0) {
            return CommonResult.success("更新成功");
        } else {
            return CommonResult.failed("更新失败");
        }
    }

    private List<SysTreePermissionVo> getPermissionTree(List<SysPermission> permissionList) {
        List<SysTreePermissionVo> treeVoList = new ArrayList<>();
        List<SysTreePermissionVo> permissionTreeVoListOri = new ArrayList<>();
        for (SysPermission permission : permissionList) {
            SysTreePermissionVo permissionTreeVo = new SysTreePermissionVo();
            BeanUtils.copyProperties(permission, permissionTreeVo);
            permissionTreeVoListOri.add(permissionTreeVo);
        }
        // match parents and children
        for (SysTreePermissionVo permissionTreeVo : permissionTreeVoListOri) {
            if (permissionTreeVo.getParentId() == 0) {  //加入菜单列表
                treeVoList.add(permissionTreeVo);
                continue;
            }
            for (SysTreePermissionVo treeVo : permissionTreeVoListOri) { //按钮列表的父id
                if (treeVo.getId().equals(permissionTreeVo.getParentId())) { //菜单的子菜单与按钮匹配
                    if (treeVo.getChildren() == null) {
                        treeVo.setChildren(new ArrayList<>()); //按钮加入菜单的子菜单
                    }
                    treeVo.getChildren().add(permissionTreeVo);  //菜单的子孩子再加入一个按钮
                }
            }
        }
        return treeVoList;
    }
    private void getBelow(List<SysTreePermissionVo> permissionTreeVoList, List<String> belowId) {
        for (SysTreePermissionVo permissionTreeVo : permissionTreeVoList) { //遍历树状结构权限列表
            if (permissionTreeVo.getChildren() == null) { //如果没有子权限
                belowId.add(String.valueOf(permissionTreeVo.getId())); //结果中添加树状Id
            } else {
                getBelow(permissionTreeVo.getChildren(), belowId); //有，递归再次判断，转换为平面结构
            }
        }
    }
}
