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

    List<SelectUserFollowListVo> selectFollowList(String username);

    List<String> selectVermicelliList(String username);
}
