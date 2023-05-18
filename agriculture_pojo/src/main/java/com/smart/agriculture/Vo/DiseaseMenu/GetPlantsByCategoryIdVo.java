package com.smart.agriculture.Vo.DiseaseMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPlantsByCategoryIdVo {
    /**
     * 植物id
     */
    @ApiModelProperty("植物id")
    private String id;

    /**
     * 类别为类别名，植物为植物名
     */
    @ApiModelProperty("类别为类别名，植物为植物名")
    private String name;

    /**
     * 菜单示例图片
     */
    @ApiModelProperty("菜单示例图片")
    private String menuSamplePicture;
}
