package com.smart.agriculture.service;

import com.smart.agriculture.Do.AskQuestionsArticleFlow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.AskQuestionsArticleFlow.AnswerQuestionDto;
import com.smart.agriculture.Dto.AskQuestionsArticleFlow.IsAdoptDto;
import com.smart.agriculture.Vo.AskQuestionsArticleFlow.GetAnswerByAskIdVo;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 *  问答帖子流程 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface IAskQuestionsArticleFlowService extends IService<AskQuestionsArticleFlow> {
    /**
     * 回答问题
     * @param dto 回复Dto
     * @return 操作状态
     */
    CommonResult<String> answerAskQuestion(AnswerQuestionDto dto);

    /**
     * 获取问答下的答复
     * @return 答复字段
     */
    CommonResult<List<GetAnswerByAskIdVo>> getAnswerByAskId(Long id);

    /**
     * 修改采纳状态
     *
     * @param dto dtu
     * @return 操作状态
     */
    CommonResult<String> isAdopt(IsAdoptDto dto);
}
