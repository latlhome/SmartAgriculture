package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.DiseaseMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  病害菜单 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface DiseaseMenuMapper extends BaseMapper<DiseaseMenu> {

    Integer selectPlantById(String plantId);
}
