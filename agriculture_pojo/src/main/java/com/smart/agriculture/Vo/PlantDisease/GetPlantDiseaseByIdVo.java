package com.smart.agriculture.Vo.PlantDisease;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPlantDiseaseByIdVo {
    /**
     * 病害id
     */
    @ApiModelProperty("病害id")
    private String id;
    /**
     * 病害名称
     */
    @ApiModelProperty("病害名称")
    private String diseaseName;

    /**
     * 病害简介
     */
    @ApiModelProperty("病害简介")
    private String introduction;


    /**
     * 示例图片 多张图片用#连接
     */
    @ApiModelProperty("示例图片--单张")
    private String samplePicture;

}
