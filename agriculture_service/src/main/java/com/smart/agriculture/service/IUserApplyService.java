package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.UserApply;
import com.smart.agriculture.Dto.UserApply.AddUserApplyDto;
import com.smart.agriculture.Dto.UserApply.UpdateUserApplyDto;
import com.smart.agriculture.Vo.UserApply.ApplyDetailsVo;
import com.smart.agriculture.Vo.UserApply.MyApplyListVo;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-21
 */
public interface IUserApplyService extends IService<UserApply> {
    /**
     * 用户申请身份
     * @param dto 申请身份Dto
     * @return 操作状态
     */
    CommonResult<String> addUserApply(AddUserApplyDto dto);

    /**
     * 删除身份申请
     * @param id 申请ID
     * @return 操作状态
     */
    CommonResult<String> deleteUserApply(String id);

    /**
     * 获取自己的申请列表
     * @return 自己的申请列表
     */
    CommonResult<List<MyApplyListVo>> getMyApplyList();

    /**
     * 根据id查询详细
     * @param id 申请Id
     * @return 详细
     */
    CommonResult<ApplyDetailsVo> getApplyOne(String id);
    /**
     * 获取所有申请列表
     * @return 申请列表
     */
    CommonResult<List<MyApplyListVo>> getAllApplyList();

    /**
     * 更新申请
     * @param dto 更新Dto
     * @return 操作状态
     */
    CommonResult<String> updateUserApply(UpdateUserApplyDto dto);
}
