package com.smart.agriculture.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.service.ISysPermissionService;
import com.smart.agriculture.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtUtils;

    @Override
    public RedisUserInfo login(String number, String password) {
        String token = null;
        RedisUserInfo redisUserInfo = new RedisUserInfo();
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(number);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtUtils.generateToken(userDetails.getUsername());

            // 获取用户所携带的按钮信息  0代表权限数据 1代表返回权限个数

            List<String> permissionValue = permissionService.getPermission(number, "0");
            redisUserInfo.setNumber(number);
            //查询用户信息
            SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getNumber, number));
            redisUserInfo.setId(user.getId());
            redisUserInfo.setName(user.getName());
            if (ObjectUtil.isNotEmpty(permissionValue)){
                redisUserInfo.setPermissionValueList(permissionValue);
            }
            redisUserInfo.setToken(tokenHead + token);
            redisUserInfo.setType(user.getType());
            redisService.set(CACHE_PUNCH_REGION, number, JSON.toJSONString(redisUserInfo),redisTime);  //保存到redis中
//            redisService.expire(number, redisTime);
        } catch (Exception e) {
            LOGGER.warn("登陆异常:{}", e.getMessage());
        }
        return redisUserInfo;
    }
}
