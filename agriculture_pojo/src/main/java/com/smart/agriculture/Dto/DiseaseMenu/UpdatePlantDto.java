package com.smart.agriculture.Dto.DiseaseMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePlantDto {
    /**
     * 植物id
     */
    @ApiModelProperty("植物id")
    private String id;
    /**
     * 植物名称
     */
    @ApiModelProperty("植物名称")
    private String categoryName;
    /**
     * 植物对应图片
     */
    @ApiModelProperty("植物对应图片地址 --单张")
    private String menuSamplePicture;
}
