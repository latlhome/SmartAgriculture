package com.visual.disease.core.extract;

import com.visual.disease.core.domain.Output;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DiseaseSearchModel {
    /**
     * 人工智能识别接口
     * @param file 图片
     * @return 结果集
     */
    List<Output> PictureEvaluation(MultipartFile file);0

    /**
     * Test接口
     * @param dir 图片目录
     * @throws IOException 异常
     */
    void Test(File dir) throws IOException;
}
