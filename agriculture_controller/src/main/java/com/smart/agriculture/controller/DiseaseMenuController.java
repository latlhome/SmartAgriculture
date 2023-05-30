package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.ByIdPage;
import com.smart.agriculture.Dto.DiseaseMenu.*;
import com.smart.agriculture.Dto.PageDto;
import com.smart.agriculture.Vo.DiseaseMenu.GetCategoryVo;
import com.smart.agriculture.Vo.DiseaseMenu.GetPlantsByCategoryIdVo;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IDiseaseMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 2：病害菜单
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/diseaseMenu")
@Api(tags = "2：病害菜单")
public class DiseaseMenuController {
    @Resource
    private IDiseaseMenuService diseaseMenuService;

    @PostMapping("/addCategory")
    @ApiOperation("增添类别")
    public CommonResult<String> addCategory(@RequestBody @Validated AddCategoryDto addCategoryDto){
        return diseaseMenuService.addCategory(addCategoryDto);
    }
    @PutMapping("/updateCategory")
    @ApiOperation("修改类别")
    public CommonResult<String> updateCategory(@RequestBody UpdateCategoryDto updateCategoryDto){
        return diseaseMenuService.updateCategory(updateCategoryDto);
    }
    @DeleteMapping("/deleteCategory/{id}")
    @ApiOperation("删除类别")
    public CommonResult<String> deleteCategory(@PathVariable("id") String id){
        return diseaseMenuService.deleteCategory(id);
    }

    @GetMapping("/getCategoryList")
    @ApiOperation("获取类别列表")
    public CommonResult<List<GetCategoryVo>> getCategoryList(PageDto pageDto){
        return diseaseMenuService.getCategory(pageDto);
    }

    @PostMapping("/addPlant")
    @ApiOperation("增添类别下植物")
    public CommonResult<String> addPlant(@RequestBody @Validated AddPlantDto addPlantDto){
        return diseaseMenuService.addPlant(addPlantDto);
    }
    @PostMapping("/updatePlant")
    @ApiOperation("修改植物")
    public CommonResult<String> updatePlant(@RequestBody @Validated UpdatePlantDto updatePlantDto){
        return diseaseMenuService.updatePlant(updatePlantDto);
    }
    @PostMapping("/deletePlant/{id}")
    @ApiOperation("删除植物")
    public CommonResult<String> deletePlant(@PathVariable("id")String id){
        return diseaseMenuService.deletePlant(id);
    }

    @GetMapping("/getPlantsByCategoryId")
    @ApiOperation("获取类别下植物列表")
    public CommonResult<List<GetPlantsByCategoryIdVo>> getPlantsByCategoryId(ByIdPage page){
        return diseaseMenuService.getPlantsByCategoryId(page);
    }

    @PostMapping("/plantToOtherCategory")
    @ApiOperation("将植物移到其他类别下")
    public CommonResult<String> plantToOtherCategory(@RequestBody PlantToOtherCategoryDto dto){
        return diseaseMenuService.plantToOtherCategory(dto);
    }
}

