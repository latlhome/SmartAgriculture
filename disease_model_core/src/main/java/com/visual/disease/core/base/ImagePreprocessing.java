package com.visual.disease.core.base;

import ai.onnxruntime.OnnxTensor;

import java.awt.image.BufferedImage;

public interface ImagePreprocessing {
    /**
     * 将图像的像素进行调整
     * @param img
     * @return
     */
     BufferedImage preprocess(BufferedImage img);

    /**
     * 将预处理后的数据转换为ONNX Tensor
     * @param img
     * @return
     */
     OnnxTensor transform(BufferedImage img);

    /**
     * 将张量进行评估
     * @param transform
     * @return
     */
     float[] predict(OnnxTensor transform);
}
