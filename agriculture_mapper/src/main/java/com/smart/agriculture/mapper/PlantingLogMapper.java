package com.smart.agriculture.mapper;

import com.smart.agriculture.Do.PlantingLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  种植日志 接口
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface PlantingLogMapper extends BaseMapper<PlantingLog> {
    /**
     * 根据ID获取种植日志
     * @param id 日志ID
     * @return 日志
     */
    PlantingLog selectPlantingLogById(Long id);
}
