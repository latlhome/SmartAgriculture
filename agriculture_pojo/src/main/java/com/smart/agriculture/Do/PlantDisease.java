package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 病害原因
     */
    @ApiModelProperty("病害原因")
    private String diseaseCause;

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
