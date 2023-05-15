package com.smart.agriculture.controller;

import com.smart.agriculture.common.result.CommonResult;
import com.visual.disease.core.extract.DiseaseSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/test")
public class CommonController {
    @Autowired
    private DiseaseSearchModel diseaseSearchModel;
    @PostMapping("/test")
    public CommonResult s(MultipartFile file){
        return CommonResult.success(diseaseSearchModel.PictureEvaluation(file));
    }
}
