package com.smart.agriculture.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Do.PlantDisease;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.Dto.PlantDisease.UpdatePlantDiseaseDto;
import com.smart.agriculture.Vo.PlantDisease.GetPlantDiseaseByIdVo;
import com.smart.agriculture.Vo.PlantDisease.SelectPlantDiseaseVo;
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
    /**
     * 添加植物下病害
     * @param addPlantDiseaseDto 增加病害Dto
     * @return 操作状态
     */
    CommonResult<String> addPlantDisease(AddPlantDiseaseDto addPlantDiseaseDto);

    /**
     * 更新植物下病害
     * @param updatePlantDiseaseDto 更新病害Dto
     * @return 操作状态
     */
    CommonResult<String> updatePlantDisease(UpdatePlantDiseaseDto updatePlantDiseaseDto);

    /**
     * 删除植物下病害
     * @param id 病害ID
     * @return 操作状态
     */
    CommonResult<String> deletePlantDisease(String id);

    /**
     * 获取植物下所有病害
     * @param page 根据ID查询的分页对象
     * @return 病害列表展示字段
     */

    CommonResult<List<GetPlantDiseaseByIdVo>> getPlantDiseasesById(ByIdPage page);

    /**
     * 查询病害详细
     * @param id 病害ID
     * @return 病害详细Vo字段
     */

    CommonResult<SelectPlantDiseaseVo> selectPlantDiseaseById(String id);

    /**
     * 对人工稚嫩返回结果进行封装
     * @param outputList 封装对象
     * @return 封装后的对象
     */

    List<Output> SecondaryProcessing(List<Output> outputList);
}
