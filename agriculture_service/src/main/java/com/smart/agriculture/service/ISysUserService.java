package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.SysUser.SysUserRegisterDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;

/**
 * <p>
 *  ISysUserService 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface ISysUserService extends IService<SysUser> {
    RedisUserInfo login(String number, String password);

    CommonResult register(SysUserRegisterDto registerDto);
}
