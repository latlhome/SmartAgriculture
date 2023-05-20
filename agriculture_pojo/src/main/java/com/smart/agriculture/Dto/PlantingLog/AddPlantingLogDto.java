package com.smart.agriculture.Dto.PlantingLog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddPlantingLogDto {

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
    /**
     * 配图 多个图片用#间隔
     */
    @ApiModelProperty("配图 多个图片用#间隔")
    private String drawing;
}
