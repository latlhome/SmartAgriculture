package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.SysUser.ChangeInformationDto;
import com.smart.agriculture.Dto.SysUser.SysUserLoginDto;
import com.smart.agriculture.Dto.SysUser.SysUserRegisterDto;
import com.smart.agriculture.Vo.SysUser.SysUserVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.security.pojo.security.RedisUserInfo;
import com.smart.agriculture.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 1：用户接口
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/sysUser")
@Api(tags="1==用户接口")
public class SysUserController {
    @Resource
    private ISysUserService userService;

    /**
     * 用户登录
     * @return redisUserInfo
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public CommonResult<RedisUserInfo> login(@RequestBody @Validated SysUserLoginDto cmsLogin){
        RedisUserInfo redisUserInfo = userService.login(cmsLogin.getUsername(), cmsLogin.getPassword());
        if (redisUserInfo.getToken() == null) {
            return CommonResult.failed("登录失败，或存在密码不正确");
        }
        return CommonResult.success(redisUserInfo);
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public CommonResult<String> login(){
        return userService.logout();
    }

    @PutMapping("/changePasswd")
    @ApiOperation(value = "修改账号密码")
    public CommonResult<String> changePasswd( @RequestParam("oldPassword") String oldPassword , @RequestParam("newPassword") String newPassword) {
        return userService.changePasswd(oldPassword,newPassword);
    }

    @PutMapping("/changeInformation")
    @ApiOperation(value = "修改账号信息")
    public CommonResult<String> changeInformation(ChangeInformationDto dto) {
        return userService.changeInformation(dto);
    }

    @GetMapping("/userdata/{username}")
    @ApiOperation(value = "获取用户信息")
    public CommonResult<SysUserVo> getUserData(@PathVariable("username") String username) {
        return userService.getUserData(username);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public CommonResult<String> register(@RequestBody @Validated SysUserRegisterDto registerDto) {
        return userService.register(registerDto);
    }

}

