package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.AskQuestionsArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  提问帖子 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface AskQuestionsArticleMapper extends BaseMapper<AskQuestionsArticle> {
    /**
     * 根据问答ID获取问答信息
     * @param id 问答ID
     * @return 问答具体信息
     */
    AskQuestionsArticle selectOneById(Long id);

    /**
     * 更改问答状态
     *
     * @param id 问答Id
     * @param code      状态ID
     * @return 是否成功
     */
    boolean setArticleState(Long id, Integer code);
}
