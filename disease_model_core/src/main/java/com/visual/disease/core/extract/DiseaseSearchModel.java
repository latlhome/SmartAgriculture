package com.visual.disease.core.extract;

import com.visual.disease.core.domain.Output;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DiseaseSearchModel {
    List<Output> PictureEvaluation(MultipartFile file);
    void Test(File dir) throws IOException;
}
