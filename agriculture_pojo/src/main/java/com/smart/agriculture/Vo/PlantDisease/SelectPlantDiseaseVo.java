package com.smart.agriculture.Vo.PlantDisease;

import lombok.Data;

import java.util.List;

@Data
public class SelectPlantDiseaseVo {
    /**
     * 病害名称
     */
    private String diseaseName;

    /**
     * 病害所属植物ID
     */
    private String plantId;
    /**
     * 病害简介
     */
    private String introduction;

    /**
     * 病害原因
     */
    private String diseaseCause;

    /**
     * 症状
     */
    private String symptom;

    /**
     * 治理方案
     */
    private String governanceScheme;

    /**
     * 示例图片 多张图片用#连接
     */
    private List<String> samplePicture;
}
