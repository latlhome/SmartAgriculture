package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.AskQuestionsArticle;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.AskQuestionsArticle.AddAskQuestionsArticleDto;
import com.smart.agriculture.Dto.AskQuestionsArticle.GetAllListDto;
import com.smart.agriculture.Dto.AskQuestionsArticle.UpdateAskQuestionsArticleDto;
import com.smart.agriculture.Vo.AskQuestionsArticle.GetAllListVo;
import com.smart.agriculture.Vo.AskQuestionsArticle.GetOneVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.enums.AskQuestionsArticle.State;
import com.smart.agriculture.mapper.AskQuestionsArticleMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.IAskQuestionsArticleService;
import com.smart.agriculture.service.IIsVoidService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  问答帖子服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class AskQuestionsArticleServiceImpl extends ServiceImpl<AskQuestionsArticleMapper, AskQuestionsArticle> implements IAskQuestionsArticleService {
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private IIsVoidService isVoidService;
    @Override
    public CommonResult<String> addAskQuestionsArticle(AddAskQuestionsArticleDto dto) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (StringUtils.isBlank(username)) return  CommonResult.failed("登录后重试！");
        if (!isVoidService.plantIsExist(Long.valueOf(dto.getPlantId()))) return CommonResult.failed("植物不存在!");
        AskQuestionsArticle askQuestionsArticle = new AskQuestionsArticle();
        BeanUtil.copyProperties(dto,askQuestionsArticle);
        askQuestionsArticle.setAuthorUsername(username);
        askQuestionsArticle.setState(-1);
        int i = baseMapper.insert(askQuestionsArticle);
        if (i>0) return CommonResult.success("发布成功！");
        return CommonResult.failed("发布失败！");
    }

    @Override
    public CommonResult<String> updateAskQuestionsArticle(UpdateAskQuestionsArticleDto dto) {
        AskQuestionsArticle ask = baseMapper.selectOneById(dto.getId());
        if (!isVoidService.askQuestionsArticleIsExist(dto.getId())) return CommonResult.failed("问题不存在!");
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (!Objects.equals(username, ask.getAuthorUsername())) return CommonResult.failed("您没有权限修改该问题！");
        if (ask.getState().equals(State.resolved.getCode())) return CommonResult.failed("已解决的问题不可修改！");
        AskQuestionsArticle flag = new AskQuestionsArticle();
        BeanUtil.copyProperties(dto,flag);
        int i = baseMapper.updateById(flag);
        if (i>0) return CommonResult.success("更新成功！");
        return CommonResult.failed("更新失败！");
    }

    @Override
    public CommonResult<String> deleteAskQuestionsArticle(Long id) {
        AskQuestionsArticle ask = baseMapper.selectOneById(id);
        if (!isVoidService.askQuestionsArticleIsExist(id)) return CommonResult.failed("问题不存在!");
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (!Objects.equals(username, ask.getAuthorUsername())) return CommonResult.failed("您没有权限删除该问题！");
        if (ask.getState().equals(State.resolved.getCode())) return CommonResult.failed("已解决的问题不可删除！");
        int i = baseMapper.deleteById(id);
        if (i>0) return CommonResult.success("删除成功！");
        return CommonResult.failed("删除失败！");
    }

    @Override
    public CommonResult<PageVo<GetAllListVo>> getAllList(GetAllListDto dto) {
        List<GetAllListVo> getAllListVos = new ArrayList<>();
        IPage<AskQuestionsArticle> page = new Page<>(dto.getPageNum(),dto.getPageSize());
        LambdaQueryWrapper<AskQuestionsArticle> lambda = new QueryWrapper<AskQuestionsArticle>().lambda();
        if (ObjectUtil.isNotNull(dto.getState())) lambda.eq(AskQuestionsArticle::getState,dto.getState());
        lambda.orderByDesc(AskQuestionsArticle::getCreateTime);
        IPage<AskQuestionsArticle> data = baseMapper.selectPage(page, lambda);
        for (AskQuestionsArticle record : data.getRecords()) {
            GetAllListVo flag = new GetAllListVo();
            BeanUtil.copyProperties(record,flag);
            flag.setDrawing(Arrays.asList(record.getDrawing().split("#")));
            getAllListVos.add(flag);
        }
        PageVo<GetAllListVo> vo = new PageVo<>();
        vo.setData(getAllListVos);
        vo.setTotalSize(page.getTotal());
        vo.setPageSize(page.getSize());
        vo.setPageNum(page.getCurrent());
        vo.setTotalPages(page.getPages());
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult<GetOneVo> getOne(Long id) {
        AskQuestionsArticle ask = baseMapper.selectOneById(id);
        if (ObjectUtil.isNull(ask)) return CommonResult.failed("问题不存在!");
        SysUser sysUser = sysUserMapper.selectOneByUsername(ask.getAuthorUsername());
        GetOneVo getOneVo = new GetOneVo();
        BeanUtil.copyProperties(ask,getOneVo);
        getOneVo.setDrawing(Arrays.asList(ask.getDrawing().split("#")));
        SysUserArticleVo sysUserArticleVo = new SysUserArticleVo();
        sysUserArticleVo.setAuthorPicture(sysUser.getHeadPicture());
        sysUserArticleVo.setAuthorNickname(sysUser.getNickname());
        sysUserArticleVo.setAuthorUsername(sysUser.getUsername());
        getOneVo.setUserArticleVo(sysUserArticleVo);
        return CommonResult.success(getOneVo);
    }
}
