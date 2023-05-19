package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Bo.AdminAuthData;
import com.smart.agriculture.Do.SysRole;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.SysUser.ChangeInformationDto;
import com.smart.agriculture.Dto.SysUser.SysUserRegisterDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.common.result.ResultCode;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.mapper.SysRoleMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.security.config.CustomAuthorizeException;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.security.service.IRedisService;
import com.smart.agriculture.service.ISysPermissionService;
import com.smart.agriculture.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  SysUserServiceImpl 服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private static final String CACHE_PUNCH_REGION = "USER";
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUser.class);


    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.expiration}")
    private Long redisTime;

    @Resource
    private ISysPermissionService permissionService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenUtil jwtUtils;
    @Resource
    private SysUserMapper userMapper;
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private IRedisService redisService;
    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public RedisUserInfo login(String username, String password) {
        String token = null;
        RedisUserInfo redisUserInfo = new RedisUserInfo();
        SysUser sysUser = baseMapper.selectOneByUsername(username);
        try {
            if (!passwordEncoder.matches(password, sysUser.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            token = jwtUtils.generateToken(sysUser.getUsername());

            // 获取用户所携带的按钮信息  0代表权限数据 1代表返回权限个数
            List<String> permissionValue = permissionService.getPermission(username, "0");
            redisUserInfo.setUsername(username);
            //查询用户信息
            if (ObjectUtil.isNotEmpty(permissionValue)){
                redisUserInfo.setPermissionValueList(permissionValue);
            }
            redisUserInfo.setToken(tokenHead + token);
            redisUserInfo.setUserType(sysUser.getUserType());
            redisService.setToken(username,redisUserInfo);  //保存到redis中
        } catch (Exception e) {
            LOGGER.warn("登陆异常:{}", e.getMessage());
        }
        return redisUserInfo;
    }

    @Override
    public CommonResult<String> register(SysUserRegisterDto registerDto) {
        if (StringUtils.isEmpty(registerDto.getPassword())) {
            registerDto.setPassword("666666");  //默认注册密码
        }
        SysUser userInfo = userMapper.selectOne(new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getUsername, registerDto.getUsername()));

        if (ObjectUtil.isNull(userInfo)) {
            SysRole sysRole;
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(registerDto, sysUser);
            sysRole = roleMapper.selectOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getTypeNumber,1));//获取普通用户权限
            sysUser.setUserType(1);
//            }
            //设置权限
            //将对象转为字符串

            List<String> roleId = new ArrayList<>();
            roleId.add(sysRole.getId().toString());
            AdminAuthData adminAuthData = new AdminAuthData();
            adminAuthData.setAuthList(roleId);
            sysUser.setAuthData(JSONUtil.toJsonStr(adminAuthData));
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
            sysUser.setSex(registerDto.getSex());
            int result = userMapper.insert(sysUser);
            if (result == 1) {
                return CommonResult.success("注册成功");
            }
        }
        return CommonResult.failed("注册失败，该账号已存在");
    }

    @Override
    public CommonResult<String> logout() {
        String username = jwtUtils.getUsernameByRequest(httpServletRequest);
        if(username == null) {
            throw  new CustomAuthorizeException(ResultCode.NO_PERMITION);
        }
        Boolean remove = redisService.remove(CACHE_PUNCH_REGION + username);
        if (remove) return CommonResult.success("退出成功");
        return CommonResult.failed("未知错误!");
    }

    @Override
    public CommonResult<String> changePasswd(String oldPassword,String newPassword) {
        String username = jwtUtils.getUsernameByRequest(httpServletRequest);
        if (StringUtils.isBlank(username)) return CommonResult.failed("未登录！");
        SysUser sysUser = userMapper.selectOneByUsername(username);
        boolean matches = passwordEncoder.matches(oldPassword, sysUser.getPassword());
        if (!matches) return  CommonResult.failed("原始密码错误！");
        SysUser flag = new SysUser();
        flag.setId(sysUser.getId());
        flag.setPassword(passwordEncoder.encode(newPassword));
        int i = userMapper.updateById(flag);
        if (i>0) return CommonResult.success("修改成功！");
        return CommonResult.failed("修改未知失败！");
    }

    @Override
    public CommonResult<String> changeInformation(ChangeInformationDto dto) {
        String username = jwtUtils.getUsernameByRequest(httpServletRequest);
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(dto,sysUser);
        int update = baseMapper.update(sysUser, new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, username));
        if (update>0) return CommonResult.success("修改成功！");
        return CommonResult.failed("修改未知错误！");
    }
}
