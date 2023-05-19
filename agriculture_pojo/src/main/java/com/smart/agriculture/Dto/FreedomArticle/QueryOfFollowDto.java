package com.smart.agriculture.Dto.FreedomArticle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryOfFollowDto {
    /**
     * 最大时间戳
     */
    @ApiModelProperty("最大时间戳")
    private Long max;
    /**
     * 偏移量
     */
    @ApiModelProperty("偏移量")
    private Integer offset;

    /**
     * 查询量
     */
    @ApiModelProperty("查询量")
    private Integer count;
}
