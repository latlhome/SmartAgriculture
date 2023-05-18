package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.DiseaseMenu.*;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IDiseaseMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/diseaseMenu")
@Api(tags = "病害菜单")
public class DiseaseMenuController {
    @Resource
    private IDiseaseMenuService diseaseMenuService;

    @PostMapping("/addCategory")
    @ApiOperation("增添类别")
    public CommonResult addCategory(@RequestBody @Validated AddCategoryDto addCategoryDto){
        return diseaseMenuService.addCategory(addCategoryDto);
    }
    @PutMapping("/updateCategory")
    @ApiOperation("修改类别")
    public CommonResult updateCategory(@RequestBody @Validated UpdateCategoryDto updateCategoryDto){
        return diseaseMenuService.updateCategory(updateCategoryDto);
    }
    @DeleteMapping("/deleteCategory/{id}")
    @ApiOperation("删除类别")
    public CommonResult deleteCategory(@PathVariable("id") String id){
        return diseaseMenuService.deleteCategory(id);
    }

    @GetMapping("/getCategoryList")
    @ApiOperation("获取类别列表")
    public CommonResult getCategoryList(){
        return diseaseMenuService.getCategory();
    }

    @PostMapping("/addPlant")
    @ApiOperation("增添类别下植物")
    public CommonResult addPlant(@RequestBody @Validated AddPlantDto addPlantDto){
        return diseaseMenuService.addPlant(addPlantDto);
    }
    @PostMapping("/updatePlant")
    @ApiOperation("修改植物")
    public CommonResult updatePlant(@RequestBody @Validated UpdatePlantDto updatePlantDto){
        return diseaseMenuService.updatePlant(updatePlantDto);
    }
    @PostMapping("/deletePlant/{id}")
    @ApiOperation("删除植物")
    public CommonResult deletePlant(@PathVariable("id")String id){
        return diseaseMenuService.deletePlant(id);
    }

    @GetMapping("/getPlantsByCategoryId/{id}")
    @ApiOperation("获取类别下植物列表")
    public CommonResult getPlantsByCategoryId(@PathVariable("id")String id){
        return diseaseMenuService.getPlantsByCategoryId(id);
    }

    @GetMapping("/plantToOtherCategory")
    @ApiOperation("将植物移到其他类别下")
    public CommonResult plantToOtherCategory(PlantToOtherCategoryDto dto){
        return diseaseMenuService.plantToOtherCategory(dto);
    }
}

