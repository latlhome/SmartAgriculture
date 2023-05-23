package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Do.UserFeedback;
import com.smart.agriculture.Dto.UserFeedback.AddUserFeedbackDto;
import com.smart.agriculture.Dto.UserFeedback.UpdateUserFeedbackDto;
import com.smart.agriculture.Dto.UserFeedback.UserFeedbackListDto;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import com.smart.agriculture.Vo.UserFeedback.UserFeedbackListVo;
import com.smart.agriculture.Vo.UserFeedback.UserFeedbackVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.enums.UserFeedback.UserFeedbackState;
import com.smart.agriculture.mapper.UserFeedbackMapper;
import com.smart.agriculture.service.IIsVoidService;
import com.smart.agriculture.service.IMessagesListService;
import com.smart.agriculture.service.IUserFeedbackService;
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
 * @since 2023-05-22
 */
@Service
public class UserFeedbackServiceImpl extends ServiceImpl<UserFeedbackMapper, UserFeedback> implements IUserFeedbackService {
    @Resource
    private IIsVoidService isVoidService;
    @Resource
    private IMessagesListService messagesListService;
    @Override
    public CommonResult<String> addUserFeedback(AddUserFeedbackDto dto) {
        String username = isVoidService.isLogin();
        UserFeedback userFeedback = new UserFeedback();
        BeanUtil.copyProperties(dto,userFeedback);
        userFeedback.setState(UserFeedbackState.unprocessed.getCode());
        userFeedback.setUsername(username);
        int insert = baseMapper.insert(userFeedback);
        if (insert>0) return CommonResult.success("反馈成功!反馈通知会在系统消息中收到!");
        return CommonResult.failed("出现错误！");
    }

    @Override
    public CommonResult<PageVo<UserFeedbackListVo>> getUserFeedbackList(UserFeedbackListDto dto) {
        PageVo<UserFeedbackListVo> vo = new PageVo<>();
        List<UserFeedbackListVo> vos = new ArrayList<>();
        LambdaQueryWrapper<UserFeedback> lambda = new QueryWrapper<UserFeedback>().lambda();
        if (ObjectUtil.isNotNull(dto.getState())) lambda.eq(UserFeedback::getState,dto.getState());
        Page<UserFeedback> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        IPage<UserFeedback> userFeedbackIPage = baseMapper.selectPage(page, lambda);
        for (UserFeedback record : userFeedbackIPage.getRecords()) {
            UserFeedbackListVo flag = new UserFeedbackListVo();
            BeanUtil.copyProperties(record,flag);
            vos.add(flag);
        }
        vo.setData(vos);
        vo.setTotalSize(page.getTotal());
        vo.setPageSize(page.getSize());
        vo.setPageNum(page.getCurrent());
        vo.setTotalPages(page.getPages());
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult<UserFeedbackVo> getUserFeedback(Long id) {
        UserFeedback userFeedback = baseMapper.selectOneById(id);
        if (ObjectUtil.isNull(userFeedback)) return CommonResult.failed("反馈不存在！");
        SysUser sysUser = isVoidService.userIsExist(userFeedback.getUsername());
        SysUserArticleVo sysUserArticleVo = new SysUserArticleVo();
        sysUserArticleVo.setAuthorNickname(sysUser.getNickname());
        sysUserArticleVo.setAuthorPicture(sysUser.getHeadPicture());
        sysUserArticleVo.setAuthorUsername(sysUser.getUsername());
        UserFeedbackVo vo = new UserFeedbackVo();
        BeanUtil.copyProperties(userFeedback,vo);
        vo.setUserArticleVo(sysUserArticleVo);
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult<String> updateUserFeedback(UpdateUserFeedbackDto dto) {
        UserFeedback s = baseMapper.selectOneById(dto.getId());
        if (Objects.equals(s.getState(), UserFeedbackState.resolved.getCode())) return CommonResult.failed("已解决状态下的反馈不能修改!");
        UserFeedback userFeedback = new UserFeedback();
        BeanUtil.copyProperties(dto,userFeedback);
        int i = baseMapper.updateById(userFeedback);
        if (i>0){
            if (ObjectUtil.isNotNull(dto.getAnswer())){
                //如果开发者回复不为空，则发送到用户收件箱里
                messagesListService.sendSystemMessage(s.getUsername(),dto.getAnswer());
                return CommonResult.success("修改成功！");
            }
            return CommonResult.success("修改成功！");
        }
        return CommonResult.failed("未知错误！");
    }
}
