package com.smart.agriculture.controller;

import com.smart.agriculture.common.result.CommonResult;
import com.visual.disease.core.extract.DiseaseSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/test")
public class CommonController {
    @Autowired
    private DiseaseSearchModel diseaseSearchModel;
    @PostMapping("/test")
    public CommonResult s(MultipartFile file){
        return CommonResult.success(diseaseSearchModel.PictureEvaluation(file));
    }

    @PostMapping("/test2")
    public void s() throws IOException {
        diseaseSearchModel.Test(new File("D:\\Study_need\\IMGS\\onnx"));
    }
}
