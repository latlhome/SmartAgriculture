package com.visual.disease.core.extract;

import com.visual.disease.core.domain.Output;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DiseaseSearchModel {
    List<Output> PictureEvaluation(MultipartFile file);
}
