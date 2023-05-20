package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.AskQuestionsArticle;
import com.smart.agriculture.Dto.AskQuestionsArticle.AddAskQuestionsArticleDto;
import com.smart.agriculture.Dto.AskQuestionsArticle.GetAllListDto;
import com.smart.agriculture.Dto.AskQuestionsArticle.UpdateAskQuestionsArticleDto;
import com.smart.agriculture.Vo.AskQuestionsArticle.GetAllListVo;
import com.smart.agriculture.Vo.AskQuestionsArticle.GetOneVo;
import com.smart.agriculture.Vo.PageVo;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  问答帖子 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface IAskQuestionsArticleService extends IService<AskQuestionsArticle> {
    /**
     * 创建一个新问答
     * @param dto 新增问答Dto
     * @return 操作状态
     */
    CommonResult<String> addAskQuestionsArticle(AddAskQuestionsArticleDto dto);

    /**
     * 更新问答
     * @param dto 更新问答Dto
     * @return 操作状态
     */
    CommonResult<String> updateAskQuestionsArticle(UpdateAskQuestionsArticleDto dto);

    /**
     * 删除问答
     * @param id 问答ID
     * @return 操作状态
     */
    CommonResult<String> deleteAskQuestionsArticle(Long id);

    /**
     * 获取列表
     * @param dto dto
     * @return 列表
     */
    CommonResult<PageVo<GetAllListVo>> getAllList(GetAllListDto dto);

    /**
     * 获取单个问答详细
     * @param id id
     * @return 问答详细
     */
    CommonResult<GetOneVo> getOne(Long id);
}
