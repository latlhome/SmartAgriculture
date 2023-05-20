package com.smart.agriculture.service;

import com.smart.agriculture.Do.DiseaseMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.DiseaseMenu.*;
import com.smart.agriculture.Dto.PageDto;
import com.smart.agriculture.Vo.DiseaseMenu.GetCategoryVo;
import com.smart.agriculture.Vo.DiseaseMenu.GetPlantsByCategoryIdVo;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 *  病害菜单 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface IDiseaseMenuService extends IService<DiseaseMenu> {
    /**
     * 添加植物类别
     * @param addCategoryDto 植物类别Dto
     * @return 操作状态
     */

    CommonResult<String> addCategory(AddCategoryDto addCategoryDto);

    /**
     * 添加植物
     * @param addPlantDto 添加植物Dto
     * @return 操作状态
     */

    CommonResult<String> addPlant(AddPlantDto addPlantDto);

    /**
     * 更新植物类别
     * @param updateCategoryDto 更新Dto
     * @return 操作状态
     */

    CommonResult<String> updateCategory(UpdateCategoryDto updateCategoryDto);

    /**
     * 删除类别
     * @param id 类别ID
     * @return 操作状态
     */

    CommonResult<String> deleteCategory(String id);

    /**
     *获取类别列表
     * @param pageDto 类别分类Dto
     * @return 类别列表
     */

    CommonResult<List<GetCategoryVo>> getCategory(PageDto pageDto);

    /**
     * 更新植物
     * @param updatePlantDto 更新植物Dto
     * @return 操作状态
     */

    CommonResult<String> updatePlant(UpdatePlantDto updatePlantDto);

    /**
     * 删除植物
     * @param id 植物ID
     * @return 操作状态
     */

    CommonResult<String> deletePlant(String id);

    /**
     * 获取植物列表 根据类别ID
     * @param page 类别ID 分页Dto
     * @return 类别下的植物列表
     */

    CommonResult<List<GetPlantsByCategoryIdVo>> getPlantsByCategoryId(ByIdPage page);

    /**
     * 将植物转移到其他类别之下
     * @param dto 植物转移Dto
     * @return 操作状态
     */

    CommonResult<String> plantToOtherCategory(PlantToOtherCategoryDto dto);
}
