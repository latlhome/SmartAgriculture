package com.smart.agriculture.Vo.DiseaseMenu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetCategoryVo {
    /**
     * 类别ID
     */
    @ApiModelProperty("类别ID")
    private String id;
    /**
     * 类别名称
     */
    @ApiModelProperty("类别名称")
    private String name;
    /**
     * 类别对应图片
     */
    @ApiModelProperty("类别对应图片地址 --单张")
    private String menuSamplePicture;
}
