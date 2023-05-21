package com.smart.agriculture.Dto.AskQuestionsArticleFlow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IsAdoptDto {
    /**
     * id
     */
    @ApiModelProperty("评论ID")
    private Long id;
    /**
     * 采纳状态  -1否 0待处理 1是
     */
    @ApiModelProperty("采纳状态  -1否 0待处理 1是")
    private Integer adoptState;
}
