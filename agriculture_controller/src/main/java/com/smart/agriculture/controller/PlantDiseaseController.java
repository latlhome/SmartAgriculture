package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.PlantDisease.AddPlantDiseaseDto;
import com.smart.agriculture.Dto.PlantDisease.UpdatePlantDiseaseDto;
import com.smart.agriculture.Vo.PlantDisease.GetPlantDiseaseByIdVo;
import com.smart.agriculture.Vo.PlantDisease.SelectPlantDiseaseVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IPlantDiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
@Api( tags = "2==病害数据库")
public class PlantDiseaseController {
    @Resource
    private IPlantDiseaseService plantDiseaseService;

    @PostMapping("/addPlantDisease")
    @PreAuthorize("hasAuthority('plant:disease:database:add')")
    @ApiOperation("增添植物下病害")
    public CommonResult<String> addPlantDisease(@RequestBody AddPlantDiseaseDto addPlantDiseaseDto){
        return plantDiseaseService.addPlantDisease(addPlantDiseaseDto);
    }

    @PutMapping("/updatePlantDisease")
    @PreAuthorize("hasAuthority('plant:disease:database:update')")
    @ApiOperation("更新植物下病害")
    public CommonResult<String> updatePlantDisease(@RequestBody UpdatePlantDiseaseDto updatePlantDiseaseDto){
        return plantDiseaseService.updatePlantDisease(updatePlantDiseaseDto);
    }
    @DeleteMapping("/deletePlantDisease/{id}")
    @ApiOperation("删除植物下病害")
    @PreAuthorize("hasAuthority('plant:disease:database:delete')")
    public CommonResult<String> deletePlantDisease(@PathVariable("id") String id){
        return plantDiseaseService.deletePlantDisease(id);
    }
    @GetMapping("/getPlantDiseasesById")
    @ApiOperation("查询植物下所有病害")
    public CommonResult<List<GetPlantDiseaseByIdVo>> getPlantDiseasesById(ByIdPage page){
        return plantDiseaseService.getPlantDiseasesById(page);
    }

    @GetMapping("/getPlantDiseaseById/{id}")
    @ApiOperation("根据病害ID查询病害具体内容")
    public CommonResult<SelectPlantDiseaseVo> selectPlantDiseaseById(@PathVariable("id") String id){
        return plantDiseaseService.selectPlantDiseaseById(id);
    }

}

