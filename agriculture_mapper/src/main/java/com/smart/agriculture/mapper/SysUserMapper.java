package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  SysUserMapper 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
        /**
         * 根据用户Username查询用户
         * @param username 用户username
         * @return 用户对象
         */
        SysUser selectOneByUsername(String username);
}
