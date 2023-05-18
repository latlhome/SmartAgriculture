package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.FreedomArticle;
import com.smart.agriculture.Dto.FreedomArticle.AddFreedomArticleDto;
import com.smart.agriculture.Dto.FreedomArticle.SelectFreedomArticleListDto;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  自由论坛帖子 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface IFreedomArticleService extends IService<FreedomArticle> {

    CommonResult addFreedomArticle(AddFreedomArticleDto addFreedomArticleDto);

    CommonResult selectFreedomArticleList(SelectFreedomArticleListDto dto);

    CommonResult selectFreedomArticleById(String id);
}
