package com.smart.agriculture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smart.agriculture.Do.UserFollow;
import com.smart.agriculture.Dto.UserFollow.IsFollowDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.mapper.UserFollowMapper;
import com.smart.agriculture.service.IUserFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-19
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements IUserFollowService {
    private static final String FOLLOWS = "follows";
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public CommonResult<String> follow(IsFollowDto isFollow) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        String key = FOLLOWS + username;
        // 1.判断到底是关注还是取关
        if (isFollow.getIsFollow()) {
            // 2.关注，新增数据
            UserFollow follow = new UserFollow();
            follow.setUsername(username);
            follow.setFollowUserId(isFollow.getFollowUserUsername());
            //入库
            boolean isSuccess = save(follow);
            if (isSuccess) {
                // 把关注用户的id，放入redis的set集合 SADD userId followerUserId
                stringRedisTemplate.opsForSet().add(key, isFollow.getFollowUserUsername());
            }
        } else {
            int isSuccess = baseMapper.delete(new QueryWrapper<UserFollow>().lambda()
                    .eq(UserFollow::getUsername, username)
                    .eq(UserFollow::getFollowUserId, isFollow.getFollowUserUsername()));
            if (isSuccess>0) {
                // 把关注用户的id从Redis集合中移除
                stringRedisTemplate.opsForSet().remove(key, isFollow.getFollowUserUsername());
            }
        }
        return CommonResult.success("操作成功！");
    }

    /**
     * 判断是否为关注
     * @param followUserId 博主Username
     * @return 是否为关注
     */
    @Override
    public CommonResult<Boolean> isFollow(Long followUserId) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        Integer integer = baseMapper.selectCount(new QueryWrapper<UserFollow>().lambda().eq(UserFollow::getFollowUserId, followUserId)
                .eq(UserFollow::getUsername, username));
        return CommonResult.success(integer > 0);
    }
}
