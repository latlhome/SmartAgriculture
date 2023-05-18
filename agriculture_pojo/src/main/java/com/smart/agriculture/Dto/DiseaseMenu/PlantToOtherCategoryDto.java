package com.smart.agriculture.Dto.DiseaseMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlantToOtherCategoryDto {
    /**
     * 植物ID
     */
    @ApiModelProperty("植物ID")
    private String plantId;
    /**
     * 目标类别ID
     */
    @ApiModelProperty("目标类别ID")
    private String categoryId;
}
