package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IPlantDiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/plantDisease")
@Api( tags = "病害数据库")
public class PlantDiseaseController {
    @Resource
    private IPlantDiseaseService plantDiseaseService;

    @PostMapping("/addPlantDisease")
    @ApiOperation("增添类别下植物")
    public CommonResult addPlantDisease(@RequestBody AddPlantDiseaseDto addPlantDiseaseDto){
        return plantDiseaseService.addPlantDisease(addPlantDiseaseDto);
    }

}

