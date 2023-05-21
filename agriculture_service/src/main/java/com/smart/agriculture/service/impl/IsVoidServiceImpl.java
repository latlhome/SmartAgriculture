package com.smart.agriculture.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smart.agriculture.Do.*;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.mapper.*;
import com.smart.agriculture.service.IIsVoidService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@Service
public class IsVoidServiceImpl implements IIsVoidService {
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private PlantDiseaseMapper diseaseMapper;
    @Resource
    private DiseaseMenuMapper diseaseMenuMapper;
    @Resource
    private FreedomArticleMapper freedomArticleMapper;
    @Resource
    private AskQuestionsArticleMapper askQuestionsArticleMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private PlantingLogMapper plantingLogMapper;

    @Override
    public boolean isLogin() {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        return !StringUtils.isBlank(username);
    }

    @Override
    public boolean plantIsExist(Long id) {
        Integer integer = diseaseMenuMapper.selectPlantById(String.valueOf(id));
        return integer>0;
    }

    @Override
    public boolean categoryIsExist(Long id) {
        DiseaseMenu s = diseaseMenuMapper.selectCategoryById(id);
        return ObjectUtil.isNotNull(s);
    }

    @Override
    public boolean diseaseIsExist(Long id) {
        PlantDisease plantDisease = diseaseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getId, id));
        return ObjectUtil.isNotNull(plantDisease);
    }

    @Override
    public boolean userIsExist(String username) {
        SysUser sysUser = sysUserMapper.selectOneByUsername(username);
        return ObjectUtil.isNotNull(sysUser);
    }

    @Override
    public boolean freedomArticleIsExist(Long id) {
        FreedomArticle freedomArticle = freedomArticleMapper.selectArticleById(String.valueOf(id));
        return ObjectUtil.isNotNull(freedomArticle);
    }

    @Override
    public boolean askQuestionsArticleIsExist(Long id) {
        AskQuestionsArticle askQuestionsArticle = askQuestionsArticleMapper.selectOneById(id);
        return ObjectUtil.isNotNull(askQuestionsArticle);
    }

    @Override
    public boolean commentIsExist(Long id) {
        Comment comment = commentMapper.selectOneCommentById(String.valueOf(id));
        return ObjectUtil.isNotNull(comment);
    }

    @Override
    public boolean logIsExist(Long id) {
        PlantingLog plantingLog = plantingLogMapper.selectPlantingLogById(id);
        return ObjectUtil.isNotNull(plantingLog);
    }
}
