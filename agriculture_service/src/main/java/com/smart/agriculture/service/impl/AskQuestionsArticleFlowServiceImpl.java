package com.smart.agriculture.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.Do.AskQuestionsArticle;
import com.smart.agriculture.Do.AskQuestionsArticleFlow;
import com.smart.agriculture.Do.SysUser;
import com.smart.agriculture.Dto.AskQuestionsArticleFlow.AnswerQuestionDto;
import com.smart.agriculture.Dto.AskQuestionsArticleFlow.IsAdoptDto;
import com.smart.agriculture.Vo.AskQuestionsArticleFlow.GetAnswerByAskIdVo;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.common.utils.JwtTokenUtil;
import com.smart.agriculture.enums.AskQuestionsArticle.State;
import com.smart.agriculture.enums.AskQuestionsArticleFlow.Adopt;
import com.smart.agriculture.mapper.AskQuestionsArticleFlowMapper;
import com.smart.agriculture.mapper.AskQuestionsArticleMapper;
import com.smart.agriculture.mapper.SysUserMapper;
import com.smart.agriculture.service.IAskQuestionsArticleFlowService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  问答流程 服务实现类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@Service
public class AskQuestionsArticleFlowServiceImpl extends ServiceImpl<AskQuestionsArticleFlowMapper, AskQuestionsArticleFlow> implements IAskQuestionsArticleFlowService {
    @Resource
    private AskQuestionsArticleMapper articleMapper;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private HttpServletRequest httpServletRequest;
    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public CommonResult<String> answerAskQuestion(AnswerQuestionDto dto) {
        AskQuestionsArticle article = articleMapper.selectOneById(dto.getArticleId());
        if (ObjectUtil.isNull(article)) return CommonResult.failed("文章不存在！");
        if (article.getState() != -1) return CommonResult.failed("文章该状态无法回复！");
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        AskQuestionsArticleFlow insert = new AskQuestionsArticleFlow();
        insert.setAnswer(dto.getAnswer());
        insert.setArticleId(String.valueOf(dto.getArticleId()));
        insert.setAnswerUsername(username);
        int i = baseMapper.insert(insert);
        if (i>0) {
            boolean s =articleMapper.setArticleState(dto.getArticleId(),State.unprocessed.getCode());
            if (s) return CommonResult.success("回复成功！");
            return CommonResult.failed("出错！");
        }
        return CommonResult.failed("回复失败！");
    }

    @Override
    public CommonResult<List<GetAnswerByAskIdVo>> getAnswerByAskId(Long id) {
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (StringUtils.isBlank(username)) return CommonResult.failed("请登录后重试!");
        AskQuestionsArticle article = articleMapper.selectOneById(id);
        if (ObjectUtil.isNull(article)) return CommonResult.failed("文章不存在！");
        List<GetAnswerByAskIdVo> vos = new ArrayList<>();
        //TODO 应该用多表查询懒得写
        List<AskQuestionsArticleFlow> askQuestionsArticleFlows = baseMapper.selectList(new QueryWrapper<AskQuestionsArticleFlow>().lambda()
                .eq(AskQuestionsArticleFlow::getArticleId, id)
                .orderByDesc(AskQuestionsArticleFlow::getCreateTime));
        for (AskQuestionsArticleFlow askQuestionsArticleFlow : askQuestionsArticleFlows) {
            GetAnswerByAskIdVo vo = new GetAnswerByAskIdVo();
            BeanUtil.copyProperties(askQuestionsArticleFlow,vo);
            SysUser sysUser = sysUserMapper.selectOneByUsername(askQuestionsArticleFlow.getAnswerUsername());
            SysUserArticleVo sysUserArticleVo = new SysUserArticleVo();
            sysUserArticleVo.setAuthorUsername(sysUser.getUsername());
            sysUserArticleVo.setAuthorPicture(sysUser.getHeadPicture());
            sysUserArticleVo.setAuthorNickname(sysUser.getNickname());
            vo.setUserArticleVo(sysUserArticleVo);
            vos.add(vo);
        }
        return CommonResult.success(vos);
    }

    @Override
    public CommonResult<String> isAdopt(IsAdoptDto dto) {
        AskQuestionsArticleFlow flow = baseMapper.selectAskById(dto.getId());
        if (ObjectUtil.isNull(flow)) return CommonResult.failed("回复不存在！");
        AskQuestionsArticle askQuestionsArticle = articleMapper.selectOneById(Long.valueOf(flow.getArticleId()));
        String username = jwtTokenUtil.getUsernameByRequest(httpServletRequest);
        if (!ObjectUtil.equals(username,askQuestionsArticle.getAuthorUsername())) return CommonResult.failed("您没有权限修改！");
        if (flow.getAdopt()!=0) return CommonResult.failed("已处理过的回复不能修改！");
        AskQuestionsArticleFlow flag = new AskQuestionsArticleFlow();
        flag.setAdopt(dto.getAdoptState());
        flag.setId(dto.getId());
        int i = baseMapper.updateById(flag);
        if (i>0){
            isAdoptUpdateAskState(dto.getAdoptState(),askQuestionsArticle.getId());
            return CommonResult.success("更新成功！");
        }
        return CommonResult.failed("未知错误！");
    }
    public void isAdoptUpdateAskState(Integer is,Long id){
        AskQuestionsArticle askQuestionsArticle = articleMapper.selectOneById(id);
        if (Objects.equals(is, Adopt.NotAdopted.getCode())) askQuestionsArticle.setState(State.unresolved.getCode());
        else if (Objects.equals(is,Adopt.Adopt.getCode())) askQuestionsArticle.setState(State.resolved.getCode());
        articleMapper.updateById(askQuestionsArticle);
    }
}
