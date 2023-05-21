package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.UserApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-21
 */
public interface UserApplyMapper extends BaseMapper<UserApply> {
    /**
     * 根据id 获取详细
     * @param id 申请Id
     * @return 详细
     */
    UserApply selectApplyById(String id);
}
