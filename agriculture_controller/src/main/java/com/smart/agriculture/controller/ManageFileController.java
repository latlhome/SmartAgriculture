package com.smart.agriculture.controller;

import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@Api(tags = "图片上传")
@Slf4j
@RequestMapping(value = "/images/manager")
public class ManageFileController {
    @Resource
    private IImagesService imagesService;

    @PostMapping("/upload")
    @ApiOperation("上传图片")
    public CommonResult upload(MultipartFile file){
        return CommonResult.success(imagesService.fileUpload(file));
    }

}
