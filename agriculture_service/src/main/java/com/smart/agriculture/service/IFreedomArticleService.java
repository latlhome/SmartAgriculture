package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.FreedomArticle;
import com.smart.agriculture.Dto.FreedomArticle.AddFreedomArticleDto;
import com.smart.agriculture.Dto.FreedomArticle.QueryOfFollowDto;
import com.smart.agriculture.Dto.FreedomArticle.SelectFreedomArticleListDto;
import com.smart.agriculture.Dto.PageDto;
import com.smart.agriculture.Vo.FreedomArticle.ScrollResultVo;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleListVo;
import com.smart.agriculture.Vo.FreedomArticle.SelectFreedomArticleVo;
import com.smart.agriculture.Vo.PageVo;
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

    CommonResult<String> addFreedomArticle(AddFreedomArticleDto addFreedomArticleDto);

    CommonResult<PageVo<SelectFreedomArticleListVo>> selectFreedomArticleList(SelectFreedomArticleListDto dto);

    CommonResult<SelectFreedomArticleVo> selectFreedomArticleById(String id);

    CommonResult<String> deleteFreedomArticle(String id);

    CommonResult<String> likeFreedomArticle(String id);

    CommonResult<ScrollResultVo> queryOfFollow(QueryOfFollowDto dto);

    CommonResult<PageVo<SelectFreedomArticleListVo>> queryOfCollection(PageDto dto);
}
