package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Do.UserApply;
import com.smart.agriculture.Do.UserApplyFlow;
import com.smart.agriculture.Dto.UserApply.AddUserApplyDto;
import com.smart.agriculture.Dto.UserApply.UpdateUserApplyDto;
import com.smart.agriculture.Vo.UserApply.ApplyDetailsVo;
import com.smart.agriculture.Vo.UserApply.MyApplyListVo;
import com.smart.agriculture.Vo.UserApplyFlow.ApplyFlowsVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.UserApply.IsAgree;
import com.smart.agriculture.mapper.UserApplyFlowMapper;
import com.smart.agriculture.mapper.UserApplyMapper;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IUserApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
public class UserApplyServiceImpl extends ServiceImpl<UserApplyMapper, UserApply> implements IUserApplyService {
    @Resource
    private IIsVoidService isVoidService;
    @Resource
    private UserApplyFlowMapper userApplyFlowMapper;

    @Override
    public CommonResult<String> addUserApply(AddUserApplyDto dto) {
        String username = isVoidService.isLogin();
        UserApply apply = baseMapper.selectOne(new QueryWrapper<UserApply>().lambda().eq(UserApply::getUsername, username).eq(UserApply::getIsAgree, IsAgree.await.getCode()));
        if (ObjectUtil.isNotNull(apply)) return CommonResult.failed("已有申请，请等申请流程结束在进行操作！");
        SysUser sysUser = isVoidService.userIsExist(username);
        UserApply userApply = new UserApply();
        BeanUtil.copyProperties(dto,userApply);
        userApply.setUsername(username);
        userApply.setNowRole(String.valueOf(sysUser.getUserType()));
        userApply.setIsAgree(IsAgree.await.getCode());
        int insert = baseMapper.insert(userApply);
        if (insert>0) return CommonResult.success("提交成功!");
        return CommonResult.failed("出现错误!");
    }

    @Override
    public CommonResult<String> deleteUserApply(String id) {
        String username = isVoidService.isLogin();
        if (StringUtils.isBlank(username)) return CommonResult.failed("无用户登录！");
        UserApply apply = baseMapper.selectApplyById(id);
        if (!Objects.equals(apply.getUsername(), username)) return CommonResult.failed("您无权删除！");
        int i = baseMapper.deleteById(id);
        if (i>0) return CommonResult.success("删除成功!");
        return CommonResult.failed("出现错误!");
    }

    @Override
    public CommonResult<List<MyApplyListVo>> getMyApplyList() {
        String username = isVoidService.isLogin();
        List<MyApplyListVo> vos = new ArrayList<>();
        List<UserApply> userApplies = baseMapper.selectList(new QueryWrapper<UserApply>().lambda()
                .eq(UserApply::getUsername, username)
                .orderByDesc(UserApply::getCreateTime));
        for (UserApply userApply : userApplies) {
            MyApplyListVo vo = new MyApplyListVo();
            BeanUtil.copyProperties(userApply,vo);
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @Override
    public CommonResult<ApplyDetailsVo> getApplyOne(String id) {
        UserApply apply = baseMapper.selectApplyById(id);
        ApplyDetailsVo applyDetailsVo = new ApplyDetailsVo();
        BeanUtil.copyProperties(apply,applyDetailsVo);
        List<UserApplyFlow> userApplyFlows = userApplyFlowMapper.selectList(new QueryWrapper<UserApplyFlow>().lambda()
                .eq(UserApplyFlow::getApplyId, id)
                .orderByDesc(UserApplyFlow::getCreateTime));
        List<ApplyFlowsVo> vos = new ArrayList<>();
        for (UserApplyFlow userApplyFlow : userApplyFlows) {
            ApplyFlowsVo vo = new ApplyFlowsVo();
            BeanUtil.copyProperties(userApplyFlow,vo);
            vos.add(vo);
        }
        applyDetailsVo.setApplyFlows(vos);
        return CommonResult.success(applyDetailsVo);
    }

    @Override
    public CommonResult<List<MyApplyListVo>> getAllApplyList() {
        List<MyApplyListVo> vos = new ArrayList<>();
        List<UserApply> userApplies = baseMapper.selectList(new QueryWrapper<UserApply>().lambda()
                .orderByDesc(UserApply::getCreateTime));
        for (UserApply userApply : userApplies) {
            MyApplyListVo vo = new MyApplyListVo();
            BeanUtil.copyProperties(userApply,vo);
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @Override
    public CommonResult<String> updateUserApply(UpdateUserApplyDto dto) {
        String username = isVoidService.isLogin();
        UserApply apply = baseMapper.selectApplyById(String.valueOf(dto.getId()));
        if (!Objects.equals(username, apply.getUsername())) return CommonResult.failed("干嘛改别人申请!");
        if (!Objects.equals(apply.getIsAgree(), IsAgree.await.getCode())
                && !Objects.equals(apply.getIsAgree(), IsAgree.refunded.getCode())) return CommonResult.failed("当前状态不可修改！");
        UserApply flag = new UserApply();
        BeanUtil.copyProperties(dto,flag);
        flag.setIsAgree(IsAgree.await.getCode());
        int i = baseMapper.updateById(flag);
        if (i>0) return CommonResult.success("更新成功！");
        return CommonResult.failed("出错了！");
    }

}
