package com.smart.agriculture.controller;


import com.smart.agriculture.Dto.DiseaseMenu.AddCategoryDto;
import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IDiseaseMenuService;
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
@RequestMapping("/diseaseMenu")
@Api(tags = "病害菜单")
public class DiseaseMenuController {
    @Resource
    private IDiseaseMenuService diseaseMenuService;

    @PostMapping("/addCategory")
    @ApiOperation("增添类别")
    public CommonResult addCategory(@RequestBody AddCategoryDto addCategoryDto){
        return diseaseMenuService.addCategory(addCategoryDto);
    }
}

