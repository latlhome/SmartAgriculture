package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Do.UserApply;
import com.smart.agriculture.Do.UserApplyFlow;
import com.smart.agriculture.Dto.UserApplyFlow.ApproveDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.UserApply.IsAgree;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.mapper.UserApplyFlowMapper;
import com.smart.agriculture.mapper.UserApplyMapper;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IUserApplyFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-21
 */
@Service
public class UserApplyFlowServiceImpl extends ServiceImpl<UserApplyFlowMapper, UserApplyFlow> implements IUserApplyFlowService {
    @Resource
    private IIsVoidService isVoidService;
    @Resource
    private UserApplyMapper userApplyMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public CommonResult<String> addUserApply(ApproveDto dto) {
        String username = isVoidService.isLogin();
        UserApply apply = userApplyMapper.selectApplyById(dto.getApplyId());
        if (!Objects.equals(apply.getIsAgree(), IsAgree.await.getCode()) && !Objects.equals(apply.getIsAgree(), IsAgree.refunded.getCode())) return CommonResult.failed("当前状态不可审批！");
        UserApplyFlow userApplyFlow = new UserApplyFlow();
        BeanUtil.copyProperties(dto,userApplyFlow);
        UserApplyFlow flag = userApplyFlow.setApprovalUsername(username);
        int i = baseMapper.insert(flag);
        if (i>0){
            approvalStateUpdateApply(dto.getApplyId(),dto.getApprovalState());
            return CommonResult.success("审批成功");
        }
        return CommonResult.failed("出现错误！");
    }

    private void approvalStateUpdateApply(String applyId, Integer approvalState) {
        UserApply apply = userApplyMapper.selectApplyById(applyId);
        if (Objects.equals(approvalState, IsAgree.agree.getCode())) {
            apply.setIsAgree(IsAgree.agree.getCode());
            SysUser sysUser = sysUserMapper.selectOneByUsername(apply.getUsername());
            sysUser.setUserType(Integer.valueOf(apply.getApplyRoleNumber()));
            sysUserMapper.updateById(sysUser);
        }
        else if (Objects.equals(approvalState, IsAgree.refuse.getCode())) apply.setIsAgree(IsAgree.refuse.getCode());
        else if (Objects.equals(approvalState, IsAgree.refunded.getCode())) apply.setIsAgree(IsAgree.refunded.getCode());
        userApplyMapper.updateById(apply);
    }
}
