package com.smart.agriculture.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zfm
 * @date 2022/6/24 14:31
 */
@Data
public class PageDto {
    @ApiModelProperty(value = "页码", example = "1", position = 1)
    private Long pageNum = 1L;
    @ApiModelProperty(value = "每页个数", example = "10", position = 2)

    private Long pageSize = 10L;
}
