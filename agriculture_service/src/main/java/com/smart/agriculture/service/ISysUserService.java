package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.SysUser.ChangeInformationDto;
import com.smart.agriculture.Dto.SysUser.SysUserRegisterDto;
import com.smart.agriculture.Vo.SysUser.SysUserVo;
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
    /**
     * 登录
     * @param number   用户名
     * @param password 密码
     * @return Redis用户对象
     */
    RedisUserInfo login(String number, String password);

    /**
     * 注册
     * @param registerDto 注册Dto对象
     * @return 操作结果
     */

    CommonResult<String> register(SysUserRegisterDto registerDto);

    /**
     * 登出当前账号
     * @return 操作结果
     */

    CommonResult<String> logout();

    /**
     * 变更面膜
     * @param oldPassword 老密码
     * @param newPassword 新密码
     * @return 操作结果
     */

    CommonResult<String> changePasswd(String oldPassword,String newPassword);

    /**
     * 变更身份信息
     * @param dto 身份信息Dto
     * @return 操作结果
     */

    CommonResult<String> changeInformation(ChangeInformationDto dto);

    /**
     * 获取用户信息
     * @param username 用户id
     * @return 信息结果
     */
    CommonResult<SysUserVo> getUserData(String username);
}
