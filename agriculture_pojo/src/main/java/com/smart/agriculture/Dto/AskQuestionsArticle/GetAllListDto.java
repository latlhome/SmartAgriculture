package com.smart.agriculture.Dto.AskQuestionsArticle;

import com.smart.agriculture.Dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetAllListDto extends PageDto {
    /**
     * 状态筛选 -1等待回答 0待处理 1已解决
     */
    @ApiModelProperty("状态筛选 -1等待回答 0待处理 1已解决 ")
    private Integer state;
}
