package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.PlantDisease;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.Dto.PlantDisease.UpdatePlantDiseaseDto;
import com.smart.agriculture.common.result.CommonResult;
import com.visual.disease.core.domain.Output;

import java.util.List;

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

    CommonResult updatePlantDisease(UpdatePlantDiseaseDto updatePlantDiseaseDto);

    CommonResult deletePlantDisease(String id);

    CommonResult getPlantDiseasesById(ByIdPage page);

    CommonResult selectPlantDiseaseById(String id);

    List<Output> SecondaryProcessing(List<Output> outputList);
}
