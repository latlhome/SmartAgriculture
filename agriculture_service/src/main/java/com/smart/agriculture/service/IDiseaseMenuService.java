package com.smart.agriculture.service;

import com.smart.agriculture.Do.DiseaseMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.DiseaseMenu.*;
import com.smart.agriculture.Dto.PageDto;
import com.smart.agriculture.common.result.CommonResult;

/**
 * <p>
 *  病害菜单 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface IDiseaseMenuService extends IService<DiseaseMenu> {

    CommonResult addCategory(AddCategoryDto addCategoryDto);

    CommonResult addPlant(AddPlantDto addPlantDto);

    CommonResult updateCategory(UpdateCategoryDto updateCategoryDto);

    CommonResult deleteCategory(String id);

    CommonResult getCategory(PageDto pageDto);

    CommonResult updatePlant(UpdatePlantDto updatePlantDto);

    CommonResult deletePlant(String id);

    CommonResult getPlantsByCategoryId(ByIdPage page);

    CommonResult plantToOtherCategory(PlantToOtherCategoryDto dto);
}
