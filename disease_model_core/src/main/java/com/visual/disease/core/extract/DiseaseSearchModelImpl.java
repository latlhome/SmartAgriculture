package com.visual.disease.core.extract;

import ai.onnxruntime.OnnxTensor;
import com.visual.disease.core.base.ImagePreprocessing;
import com.visual.disease.core.base.ModelReturnDispose;
import com.visual.disease.core.domain.Output;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DiseaseSearchModelImpl implements DiseaseSearchModel{
    private ImagePreprocessing imagePreprocessing;

    private ModelReturnDispose modelReturnDispose;

    public DiseaseSearchModelImpl(ImagePreprocessing imagePreprocessing, ModelReturnDispose modelReturnDispose) {
        this.imagePreprocessing = imagePreprocessing;
        this.modelReturnDispose = modelReturnDispose;
    }

    @Override
    public List<Output> PictureEvaluation(MultipartFile file) {
        InputStream inputStream = null;
        BufferedImage image = null;
        try {
            inputStream = file.getInputStream();
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedImage preprocess = imagePreprocessing.preprocess(image);
        OnnxTensor transform = imagePreprocessing.transform(preprocess);
        float[] predict = imagePreprocessing.predict(transform);

        float[] softmax = modelReturnDispose.softmax(predict);
        String[] postprocess = modelReturnDispose.postprocess(predict);
        float[] floats = modelReturnDispose.getFloats(softmax);
        List<Output> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Output output = new Output();
            output.setPostprocess(postprocess[i]);
            output.setCls_score(floats[i]);
            list.add(output);
        }
        return list;
    }
}
