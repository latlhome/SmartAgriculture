package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PlantDisease extends BaseDo{
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
