package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.FreedomArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.agriculture.Dto.FreedomArticle.AddAllArticleIdDto;

import java.util.List;

/**
 * <p>
 *  自由论坛帖子 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface FreedomArticleMapper extends BaseMapper<FreedomArticle> {
    /**
     * 查找文章根据文章ID
     * @param id 文章ID
     * @return 文章
     */
    FreedomArticle selectArticleById(String id);

    /**
     * 查询某人所有的文章ID
     * @param username 某人的Username
     * @return 某人所有的文章ID
     */

    List<String> selectOneAllArticleIdByUsername(String username);

    /**
     * 获取Username下所有的文章 ID 和 创建时间
     * @param followUserUsername 作者Username
     * @return Username下所有的文章 ID 和 创建时间
     */

    List<AddAllArticleIdDto> selectAddAllArticleId(String followUserUsername);
}
