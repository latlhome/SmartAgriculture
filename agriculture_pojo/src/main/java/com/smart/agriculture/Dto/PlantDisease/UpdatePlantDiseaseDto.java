package com.smart.agriculture.Dto.PlantDisease;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class UpdatePlantDiseaseDto {
    /**
     * 病害id
     */
    @NotBlank
    @ApiModelProperty("病害id")
    private String id;

    /**
     * 病害名称
     */
    @NotBlank
    @ApiModelProperty("病害名称")
    private String diseaseName;

    /**
     * 病害所属植物ID
     */
    @NotBlank
    @ApiModelProperty("病害所属植物ID")
    private String plantId;

    /**
     * 病害简介
     */
    @NotBlank
    @ApiModelProperty("病害简介")
    private String introduction;

    /**
     * 病害原因
     */
    @NotBlank
    @ApiModelProperty("病害原因")
    private String diseaseCause;

    /**
     * 症状
     */
    @NotBlank
    @ApiModelProperty("症状")
    private String symptom;

    /**
     * 治理方案
     */
    @NotBlank
    @ApiModelProperty("治理方案")
    private String governanceScheme;

    /**
     * 示例图片 多张图片用#连接
     */
    @NotBlank
    @ApiModelProperty("示例图片 多张图片用#连接")
    private String samplePicture;
}
