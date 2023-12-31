package com.smart.agriculture.controller;

import com.smart.agriculture.common.result.CommonResult;
import com.smart.agriculture.service.IPlantDiseaseService;
import com.visual.disease.core.domain.Output;
import com.visual.disease.core.extract.DiseaseSearchModel;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 病害识别--人工智能
 * @author ylx
 * @since 2023-05-16
 */
@RestController
@RequestMapping("/diseaseIdentification")
@Api(tags = "病害识别--人工智能")
public class CommonController {
    @Resource
    private DiseaseSearchModel diseaseSearchModel;
    @Resource
    private IPlantDiseaseService plantDiseaseService;

    @PostMapping("/forecast")
    public CommonResult<List<Output>> Identification(MultipartFile file){
        List<Output> list = diseaseSearchModel.PictureEvaluation(file);
        return CommonResult.success(plantDiseaseService.SecondaryProcessing(list));
    }
    @PostMapping("/OnlyIdentification")
    public CommonResult<List<Output>> OnlyIdentification(MultipartFile file){
        List<Output> list = diseaseSearchModel.PictureEvaluation(file);
        return CommonResult.success(list);
    }
    @PostMapping("/test2")
    public void s() throws IOException {
        diseaseSearchModel.Test(new File("D:\\Study_need\\IMGS\\onnx"));
    }
}
