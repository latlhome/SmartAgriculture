package com.smart.agriculture.service;

import com.smart.agriculture.Do.DiseaseMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.DiseaseMenu.AddCategoryDto;
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
}
