package com.smart.agriculture.Dto.DiseaseMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateCategoryDto {
    /**
     * 类别ID
     */
    @NotBlank
    @ApiModelProperty("类别ID")
    private String id;
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
