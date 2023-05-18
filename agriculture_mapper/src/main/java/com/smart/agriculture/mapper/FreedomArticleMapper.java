package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.FreedomArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  自由论坛帖子 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface FreedomArticleMapper extends BaseMapper<FreedomArticle> {

    FreedomArticle selectArticleById(String id);
}
