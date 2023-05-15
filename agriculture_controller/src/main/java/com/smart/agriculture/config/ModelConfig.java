package com.smart.agriculture.config;

import com.visual.disease.core.base.ImagePreprocessing;
import com.visual.disease.core.base.ModelReturnDispose;
import com.visual.disease.core.extract.DiseaseSearchModel;
import com.visual.disease.core.extract.DiseaseSearchModelImpl;
import com.visual.disease.core.models.ImagePreprocessingImpl;
import com.visual.disease.core.models.ModelReturnDisposeImpl;
import com.visual.disease.core.utils.PathUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("visualModelConfig")
public class ModelConfig {

    @Value("${model.path}")
    private String modelPath;
    @Value("${model.threads}")
    private int threads;

    /**
     * 图像预处理
     * @return
     */
    @Bean(name = "ImagePreprocessing")
    public ImagePreprocessing getImagePreprocessing(){
        PathUtil pathUtil = new PathUtil();
        modelPath = pathUtil.getUploadResource("model.onnx");
        return new ImagePreprocessingImpl(modelPath,threads);
    }

    /**
     * 结果解析
     * @return
     */
    @Bean(name = "ModelReturnDispose")
    public ModelReturnDispose getModelReturnDispose(){
        return new ModelReturnDisposeImpl(modelPath,threads);
    }

    @Bean(name = "DiseaseSearchModel")
    public DiseaseSearchModel getDiseaseSearchModel(
            @Qualifier("ImagePreprocessing") ImagePreprocessing imagePreprocessing,
            @Qualifier("ModelReturnDispose") ModelReturnDispose modelReturnDispose
           ){
        return new DiseaseSearchModelImpl(imagePreprocessing,modelReturnDispose);
    }
}
