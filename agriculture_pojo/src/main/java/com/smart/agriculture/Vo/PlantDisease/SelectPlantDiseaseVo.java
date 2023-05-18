package com.smart.agriculture.Vo.PlantDisease;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SelectPlantDiseaseVo {
    /**
     * 病害名称
     */
    @ApiModelProperty("病害名称")
    private String diseaseName;

    /**
     * 病害所属植物ID
     */
    @ApiModelProperty("病害所属植物ID")
    private String plantId;
    /**
     * 病害简介
     */
    @ApiModelProperty("病害简介")
    private String introduction;

    /**
     * 病害原因
     */
    @ApiModelProperty("病害原因")
    private String diseaseCause;

    /**
     * 症状
     */
    @ApiModelProperty("症状")
    private String symptom;

    /**
     * 治理方案
     */
    @ApiModelProperty("治理方案")
    private String governanceScheme;

    /**
     * 示例图片 多张图片用#连接
     */
    @ApiModelProperty("示例图片 多张图片用#连接")
    private String samplePicture;
}
