package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.PlantingLog.AddPlantingLogDto;
import com.smart.agriculture.Dto.PlantingLog.UpdatePlantingLogDto;
import com.smart.agriculture.Vo.PlantingLog.GetPlantingLogListVo;
import com.smart.agriculture.Vo.PlantingLog.GetPlantingLogVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IPlantingLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 5：种植日志
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/plantingLog")
@Api(tags = "5：种植日志")
public class PlantingLogController {
    @Resource
    private IPlantingLogService plantingLogService;
    @PostMapping("/add")
    @ApiOperation("新增日志")
    public CommonResult<String> addPlantingLog(@RequestBody AddPlantingLogDto dto){
        return plantingLogService.addPlantingLog(dto);
    }
    @PutMapping("/update")
    @ApiOperation("修改日志")
    public CommonResult<String> updatePlantingLog(@RequestBody UpdatePlantingLogDto dto){
        return plantingLogService.updatePlantingLog(dto);
    }
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除日志")
    public CommonResult<String> deletePlantingLog(@PathVariable("id") Long id){
        return plantingLogService.deletePlantingLog(id);
    }
    @GetMapping("/getList")
    @ApiOperation("获取日志列表")
    public CommonResult<List<GetPlantingLogListVo>> getPlantingLogList(){
        return plantingLogService.getPlantingLogList();
    }
    @GetMapping("/get/{id}")
    @ApiOperation("获取日志详细")
    public CommonResult<GetPlantingLogVo> getPlantingLog(@PathVariable("id") Long id){
        return plantingLogService.getPlantingLog(id);
    }

}

