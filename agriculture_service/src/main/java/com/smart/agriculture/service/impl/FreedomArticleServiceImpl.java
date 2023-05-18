package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.FreedomArticle;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.FreedomArticle.AddFreedomArticleDto;
import com.smart.agriculture.Dto.FreedomArticle.SelectFreedomArticleListDto;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleListVo;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleVo;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.mapper.CommentMapper;
import com.smart.agriculture.mapper.DiseaseMenuMapper;
import com.smart.agriculture.mapper.FreedomArticleMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.IFreedomArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  自由论坛帖子服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class FreedomArticleServiceImpl extends ServiceImpl<FreedomArticleMapper, FreedomArticle> implements IFreedomArticleService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private DiseaseMenuMapper diseaseMenuMapper;
    @Resource
    private CommentMapper commentMapper;
    @Override
    public CommonResult addFreedomArticle(AddFreedomArticleDto addFreedomArticleDto) {
        SysUser sysUser = sysUserMapper.selectOneByUsername(addFreedomArticleDto.getAuthorUsername());
        if (ObjectUtil.isNull(sysUser)) return CommonResult.failed("发布用户不存在！");
        Integer integer = diseaseMenuMapper.selectPlantById(addFreedomArticleDto.getPlantId());
        if (integer == 0) return CommonResult.failed("对应农作物不存在！");
        FreedomArticle freedomArticle = new FreedomArticle();
        BeanUtil.copyProperties(addFreedomArticleDto,freedomArticle);
        int i = baseMapper.insert(freedomArticle);
        if (i>0) return CommonResult.success("创建成功！");
        else return CommonResult.failed("创建出错!");
    }

    @Override
    public CommonResult selectFreedomArticleList(SelectFreedomArticleListDto dto) {
        List<SelectFreedomArticleListVo> vo = new ArrayList<>();
        LambdaQueryWrapper<FreedomArticle> lambda = new QueryWrapper<FreedomArticle>().lambda();
        if (ObjectUtil.isNotNull(dto.getPlantId())) {
            Integer integer = diseaseMenuMapper.selectPlantById(dto.getPlantId());
            if (integer == 0) return CommonResult.failed("对应农作物不存在！");
            lambda.eq(FreedomArticle::getPlantId,dto.getPlantId());
        }
        if (ObjectUtil.isNotNull(dto.getLike())) lambda.like(FreedomArticle::getTitle,dto.getLike());
        IPage<FreedomArticle> freedomArticleIPage = baseMapper.selectPage(new Page<>(dto.getPageNum(), dto.getPageSize()), lambda);
        for (FreedomArticle record : freedomArticleIPage.getRecords()) {
            SelectFreedomArticleListVo flag = new SelectFreedomArticleListVo();
            BeanUtil.copyProperties(record,flag);
            flag.setDrawing(Arrays.asList(record.getDrawing().split("#")));
            vo.add(flag);
        }
        return CommonResult.success(vo);
    }

    @Override
    public CommonResult selectFreedomArticleById(String id) {
        SelectFreedomArticleVo vo = new SelectFreedomArticleVo();
        FreedomArticle freedomArticle = baseMapper.selectArticleById(id);
        BeanUtil.copyProperties(freedomArticle,vo);
        vo.setDrawings(Arrays.asList(freedomArticle.getDrawing().split("#")));
        SysUser sysUser = sysUserMapper.selectOneByUsername(freedomArticle.getAuthorUsername());
        SysUserArticleVo sysUserArticleVo = new SysUserArticleVo();
        sysUserArticleVo.setAuthorUsername(sysUser.getUsername());
        sysUserArticleVo.setAuthorNickname(sysUser.getNickname());
        sysUserArticleVo.setAuthorPicture(sysUser.getHeadPicture());
        vo.setUserArticleVo(sysUserArticleVo);
        //TODO
        vo.setIsLike(null);
        vo.setIsCollect(null);
        vo.setLikeNumber(null);
        return CommonResult.success(vo);
    }
}
