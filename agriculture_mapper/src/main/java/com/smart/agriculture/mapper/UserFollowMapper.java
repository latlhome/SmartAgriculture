package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.UserFollow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.agriculture.Vo.UserFollow.SelectUserFollowListVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-19
 */
public interface UserFollowMapper extends BaseMapper<UserFollow> {
    /**
     * 查询用户关注列表
     * @param username 用户Username
     * @return 关注的用户(username.头像.昵称)List
     */
    List<SelectUserFollowListVo> selectFollowList(String username);

    /**
     *  查询用户关注所有Username
     * @param username 用户Username
     * @return 关注的UsernameList
     */

    List<String> selectVermicelliList(String username);
}
