package com.smart.agriculture.service;

import com.smart.agriculture.Do.PlantDisease;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  植物病害 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface IPlantDiseaseService extends IService<PlantDisease> {

    CommonResult addPlantDisease(AddPlantDiseaseDto addPlantDiseaseDto);
}
