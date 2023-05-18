package com.smart.agriculture.Dto.FreedomArticle;

import com.smart.agriculture.Dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SelectFreedomArticleListDto extends PageDto {
    /**
     * 按照植物id对帖子进行分类查询
     */
    @ApiModelProperty(value = "按照植物id对帖子进行分类查询")
    private String plantId;

    /**
     * 对标题进行模糊搜索
     */
    @ApiModelProperty("对标题进行模糊搜索")
    private String like;
}
