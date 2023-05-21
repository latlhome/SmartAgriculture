package com.smart.agriculture.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smart.agriculture.Do.*;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.mapper.*;
import com.smart.agriculture.service.IIsVoidService;
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
    public String isLogin() {
        return jwtTokenUtil.getUsernameByRequest(httpServletRequest);
    }

    @Override
    public boolean plantIsExist(Long id) {
        Integer integer = diseaseMenuMapper.selectPlantById(String.valueOf(id));
        return integer>0;
    }

    @Override
    public DiseaseMenu categoryIsExist(Long id) {
        return diseaseMenuMapper.selectCategoryById(id);
    }

    @Override
    public PlantDisease diseaseIsExist(Long id) {
        return diseaseMapper.selectOne(new QueryWrapper<PlantDisease>().lambda().eq(PlantDisease::getId, id));
    }

    @Override
    public SysUser userIsExist(String username) {
        return sysUserMapper.selectOneByUsername(username);
    }

    @Override
    public FreedomArticle freedomArticleIsExist(Long id) {
        return freedomArticleMapper.selectArticleById(String.valueOf(id));
    }

    @Override
    public AskQuestionsArticle askQuestionsArticleIsExist(Long id) {
        return askQuestionsArticleMapper.selectOneById(id);
    }

    @Override
    public Comment commentIsExist(Long id) {
        return commentMapper.selectOneCommentById(String.valueOf(id));
    }

    @Override
    public PlantingLog logIsExist(Long id) {
        return plantingLogMapper.selectPlantingLogById(id);
    }
}
