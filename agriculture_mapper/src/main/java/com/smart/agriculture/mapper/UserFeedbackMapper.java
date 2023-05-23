package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.UserFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-22
 */
public interface UserFeedbackMapper extends BaseMapper<UserFeedback> {
    /**
     * 根据Id差反馈
     *
     * @param id 反馈id
     * @return 反馈信息
     */
    UserFeedback selectOneById(Long id);
}
