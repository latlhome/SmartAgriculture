package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.AskQuestionsArticleFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  提问帖子流程 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface AskQuestionsArticleFlowMapper extends BaseMapper<AskQuestionsArticleFlow> {
    /**
     * 更具ID 获取答复
     * @param id 答复ID
     * @return 答复详细
     */
    AskQuestionsArticleFlow selectAskById(Long id);
}
