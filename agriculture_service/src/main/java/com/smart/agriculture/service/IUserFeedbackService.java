package com.smart.agriculture.service;

import com.smart.agriculture.Do.UserFeedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.UserFeedback.AddUserFeedbackDto;
import com.smart.agriculture.Dto.UserFeedback.UpdateUserFeedbackDto;
import com.smart.agriculture.Dto.UserFeedback.UserFeedbackListDto;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.UserFeedback.UserFeedbackListVo;
import com.smart.agriculture.Vo.UserFeedback.UserFeedbackVo;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-22
 */
public interface IUserFeedbackService extends IService<UserFeedback> {
    /**
     * 新建用户反馈
     * @param dto 用户反馈新建dto
     * @return 操作状态
     */
    CommonResult<String> addUserFeedback(AddUserFeedbackDto dto);

    /**
     * 管理员查看用户反馈
     *
     * @param dto 筛选 分页
     * @return 结果集
     */
    CommonResult<PageVo<UserFeedbackListVo>> getUserFeedbackList(UserFeedbackListDto dto);

    /**
     * 查看用户反馈详细
     * @param id 反馈id
     * @return 详细
     */
    CommonResult<UserFeedbackVo> getUserFeedback(Long id);

    /**
     * 管理员更新用户的反馈状态
     * @param dto dto
     * @return 操作状态
     */
    CommonResult<String> updateUserFeedback(UpdateUserFeedbackDto dto);
}
