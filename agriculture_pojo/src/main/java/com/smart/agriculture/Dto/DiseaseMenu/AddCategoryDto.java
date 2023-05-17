package com.smart.agriculture.Dto.DiseaseMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 添加类别
 */
@Data
public class AddCategoryDto {
    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    private String categoryName;
    /**
     * 类别对应图片
     */
    @ApiModelProperty("类别对应图片地址 --单张")
    private String menuSamplePicture;
}
