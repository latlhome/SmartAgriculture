package com.smart.agriculture.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ByIdPage extends PageDto {
    /**
     * 查询目标id
     */
    @ApiModelProperty("id")
    private String id;
}
