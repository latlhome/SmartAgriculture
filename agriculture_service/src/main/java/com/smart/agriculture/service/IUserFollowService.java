package com.smart.agriculture.service;

import com.smart.agriculture.Do.UserFollow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.UserFollow.IsFollowDto;
import com.smart.agriculture.Vo.UserFollow.SelectUserFollowListVo;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-19
 */
public interface IUserFollowService extends IService<UserFollow> {

    CommonResult<String> follow(IsFollowDto isFollow);

    CommonResult<Boolean> isFollow(Long followUserId);

    CommonResult<List<SelectUserFollowListVo>> selectFollowList();
}
