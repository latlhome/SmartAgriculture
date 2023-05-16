package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.SysUser.SysUserLoginDto;
import com.smart.agriculture.Dto.SysUser.SysUserRegisterDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags="用户接口")
public class SysUserController {
    @Resource
    private ISysUserService userService;

    /**
     * 用户登录
     * @return redisUserInfo
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public CommonResult login(@RequestBody @Validated SysUserLoginDto cmsLogin){
        RedisUserInfo redisUserInfo = userService.login(cmsLogin.getUsername(), cmsLogin.getPassword());
        if (redisUserInfo.getToken() == null) {
            return CommonResult.failed("登录失败，或存在密码不正确");
        }
        return CommonResult.success(redisUserInfo);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public CommonResult register(@RequestBody @Validated SysUserRegisterDto registerDto) {
        return userService.register(registerDto);
    }

}

