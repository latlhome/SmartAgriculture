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
    /**
     * 根据植物id查寻相对应的植物有几个
     * @param plantId 植物ID
     * @return 查询到的个数
     */
    Integer selectPlantById(String plantId);
}
