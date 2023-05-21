package com.smart.agriculture.service;

import com.smart.agriculture.Do.UserApplyFlow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.UserApplyFlow.ApproveDto;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-21
 */
public interface IUserApplyFlowService extends IService<UserApplyFlow> {
    /**
     * 审批
     * @param dto 审批要的东西
     * @return 操作状态
     */
    CommonResult<String> addUserApply(ApproveDto dto);
}
