package com.smart.agriculture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.UserCollection;
import com.smart.agriculture.Dto.UserCollection.IsCollectionDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.mapper.UserCollectionMapper;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IUserCollectionService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.smart.agriculture.common.config.RedisConstants.COLLECTION;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-22
 */
@Service
public class UserCollectionServiceImpl extends ServiceImpl<UserCollectionMapper, UserCollection> implements IUserCollectionService {
    @Resource
    private IIsVoidService isVoidService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public CommonResult<String> collection(IsCollectionDto dto) {
        String username = isVoidService.isLogin();
        String key = COLLECTION + username;
        // 1.判断到底是收藏还是取消
        if (!dto.getIsCollection()) {
            // 2.关注，新增数据
            UserCollection collection = new UserCollection();
            collection.setUsername(username);
            collection.setCollectionId(dto.getCollectionId());
            //入库
            int insert = baseMapper.insert(collection);
            if (insert >0) {
                // 把收藏帖子的id，放入redis的set集合
                stringRedisTemplate.opsForSet().add(key,dto.getCollectionId());
                return CommonResult.success("收藏成功!");
            }
        } else {
            int isSuccess = baseMapper.delete(new QueryWrapper<UserCollection>().lambda()
                    .eq(UserCollection::getUsername, username)
                    .eq(UserCollection::getCollectionId, dto.getIsCollection()));
            if (isSuccess>0) {
                // 把收藏的id从Redis集合中移除
                stringRedisTemplate.opsForSet().remove(key, dto.getCollectionId());
                return CommonResult.success("取消成功！");
            }
        }
        return CommonResult.success("操作有误！");
    }
}
