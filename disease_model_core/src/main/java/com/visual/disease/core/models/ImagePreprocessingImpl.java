package com.visual.disease.core.models;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import com.visual.disease.core.base.BaseOnnxInfer;
import com.visual.disease.core.base.ImagePreprocessing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;

public class ImagePreprocessingImpl extends BaseOnnxInfer implements ImagePreprocessing {
    /**
     * 构造函数
     *
     * @param modelPath
     * @param threads
     */
    public ImagePreprocessingImpl(String modelPath, int threads) {
        super(modelPath, threads);
    }

    /**
     * 将图像的像素进行调整
     * @param img
     * @return
     */
    @Override
     public BufferedImage preprocess(BufferedImage img){
        int targetWidth = 224;
        int targetHeight = 224;
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(img, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resized;
    }

    /**
     * 将预处理后的数据转换为ONNX Tensor
     * @param img
     * @return
     */
    @Override
    public OnnxTensor transform(BufferedImage img){
        float[][][][] pixelData = new float[1][3][224][224];
        // 获取图像的 RGB 像素值并进行归一化
        for (int i = 0; i < 224; ++i) {
            for (int j = 0; j < 224; ++j) {
                int rgb = img.getRGB(j, i);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                pixelData[0][0][i][j] = (float) (Math.round(Math.min(Math.max(((float)r / 255.0f - 0.4737f) / 0.1920f, 0), 1) * 10000) / 10000.0);
                pixelData[0][1][i][j] = (float) (Math.round(Math.min(Math.max(((float)g / 255.0f - 0.4948f) / 0.1592f, 0), 1) * 10000) / 10000.0);
                pixelData[0][2][i][j] = (float) (Math.round(Math.min(Math.max(((float)b / 255.0f - 0.4336f) / 0.2184f, 0), 1) * 10000) / 10000.0);

            }
        }
        OnnxTensor inputTensor = null;
        try {
            inputTensor = OnnxTensor.createTensor(OrtEnvironment.getEnvironment(), pixelData);
        } catch (OrtException e) {
            throw new RuntimeException(e);
        }
        return inputTensor;
    }

    /**
     * 将张量进行评估
     * @param transform
     * @return
     */
    @Override
    public float[] predict(OnnxTensor transform){

        float[][] s;
        OrtSession.Result output = null;
        try {
            output = getSession().run(Collections.singletonMap(getInputName(), transform));
            s = (float[][]) output.get(0).getValue();
        } catch (OrtException e) {
            throw new RuntimeException(e);
        }
        return s[0];
    }
}
