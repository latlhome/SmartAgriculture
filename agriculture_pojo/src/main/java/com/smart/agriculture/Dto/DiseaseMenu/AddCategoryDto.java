package com.smart.agriculture.Dto.DiseaseMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 添加类别
 */
@Data
public class AddCategoryDto {
    /**
     * 类别名称
     */
    @NotBlank
    @ApiModelProperty("类别名称")
    private String categoryName;
    /**
     * 类别对应图片
     */
    @NotBlank
    @ApiModelProperty("类别对应图片地址 --单张")
    private String menuSamplePicture;
}
