package com.smart.agriculture.service;

import com.smart.agriculture.Do.PlantingLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.Dto.PlantingLog.AddPlantingLogDto;
import com.smart.agriculture.Dto.PlantingLog.UpdatePlantingLogDto;
import com.smart.agriculture.Vo.PlantingLog.GetPlantingLogListVo;
import com.smart.agriculture.Vo.PlantingLog.GetPlantingLogVo;
import com.smart.agriculture.common.result.CommonResult;

import java.util.List;

/**
 * <p>
 *  种植日志 服务类
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
public interface IPlantingLogService extends IService<PlantingLog> {
    /**
     * 新增日志
     * @param dto 日志新增Dto
     * @return 结果
     */

    CommonResult<String> addPlantingLog(AddPlantingLogDto dto);
    /**
     * 修改日志
     * @param dto 日志修改Dto
     * @return 结果
     */

    CommonResult<String> updatePlantingLog(UpdatePlantingLogDto dto);

    /**
     * 删除日志
     * @param id 日志ID
     * @return 操作状态
     */
    CommonResult<String> deletePlantingLog(Long id);

    /**
     * 获取日志List
     * @return 日志ListVo对象
     */

    CommonResult<List<GetPlantingLogListVo>> getPlantingLogList();

    /**
     * 获取日志详细
     * @param id 日志ID
     * @return 日志详细
     */
    CommonResult<GetPlantingLogVo> getPlantingLog(Long id);
}
