package com.smart.agriculture.service;

import com.smart.agriculture.Do.UserCollection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.UserCollection.IsCollectionDto;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-22
 */
public interface IUserCollectionService extends IService<UserCollection> {
    /**
     * 收藏 or 取消
     * @param dto 收藏与否dto
     * @return 操作状态
     */
    CommonResult<String> collection(IsCollectionDto dto);
}
