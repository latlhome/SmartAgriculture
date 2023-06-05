package com.visual.disease.core.extract;

import ai.onnxruntime.OnnxTensor;
import com.visual.disease.core.base.ImagePreprocessing;
import com.visual.disease.core.base.ModelReturnDispose;
import com.visual.disease.core.domain.Output;
import com.visual.disease.core.utils.ImageMat;
import com.visual.disease.core.utils.Util;
import org.opencv.core.Scalar;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiseaseSearchModelImpl implements DiseaseSearchModel{

    static float sum = 0;
    static float success = 0;
    static float failure = 0;
    static float ss = 0;
    static float time = 0;
    static List<String> errorList = new ArrayList<>();
    private final ImagePreprocessing imagePreprocessing;

    private final ModelReturnDispose modelReturnDispose;

    public DiseaseSearchModelImpl(ImagePreprocessing imagePreprocessing, ModelReturnDispose modelReturnDispose) {
        this.imagePreprocessing = imagePreprocessing;
        this.modelReturnDispose = modelReturnDispose;
    }

    @Override
    public List<Output> PictureEvaluation(MultipartFile file) {
        ImageMat imageMat;

        try {
            imageMat = ImageMat.fromInputStream(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageMat imageMats = imageMat.clone();
        OnnxTensor onnxTensor = imageMats.resizeAndNoReleaseMat(224, 224)
                .blobFromImageAndDoReleaseMat(1.0 / 127.5, new Scalar(127.5, 127.5, 127.5), true)
                .to4dFloatOnnxTensorAndDoReleaseMat(true);

        float[] predict = imagePreprocessing.predict(onnxTensor);
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

    @Override
    public void Test(File dir) {
        te(dir);
        System.out.println("预测个数: "+sum);
        System.out.println("正确个数: "+success);
        System.out.println("错误个数: "+failure);
        System.out.println("预测平均置信度: "+ss/sum +"%");
        System.out.println("预测准确率: "+success/sum*100 +"%");
        System.out.println("单个图片预测时间: "+time/sum +"ms");
        System.out.println("错误列表"+errorList.toString());
    }

    public void te(File dir){
        if (dir.isDirectory()) {  // 如果是目录，则遍历它
            File[] files = dir.listFiles();  // 获取目录下所有文件和子目录
            if (files != null) {
                for (File file : files) {
                    Test(file);  // 递归遍历目录下每个文件和子目录
                }
            }
        } else {  // 如果不是目录，则处理它

            ImageMat imageMat = null;
            try {
                FileInputStream inputStream = new FileInputStream(dir);
                imageMat = ImageMat.fromInputStream(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ImageMat imageMats = imageMat.clone();
            OnnxTensor onnxTensor = imageMats.resizeAndNoReleaseMat(224, 224)
                    .blobFromImageAndDoReleaseMat(1.0 / 127.5, new Scalar(127.5, 127.5, 127.5), true)
                    .to4dFloatOnnxTensorAndDoReleaseMat(true);
            try {
                Util.saveNormalizedTensorAsImage(onnxTensor,"D:\\Study_need\\IMGS\\out\\h.png");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            long stime = System.currentTimeMillis();
            float[] predict = imagePreprocessing.predict(onnxTensor);
            float[] softmax = modelReturnDispose.softmax(predict);
            String[] postprocess = modelReturnDispose.postprocess(predict);
            float[] floats = modelReturnDispose.getFloats(softmax);
            long etime = System.currentTimeMillis();
            System.out.println("---------------------------------");
            System.out.println("预测类型: " + postprocess[0]);
            System.out.println("可信度: " + floats[0]*100 +"%");
            if (dir.getName().substring(0,5).equals(postprocess[0])){
                System.out.println("ture");
                success++;
            }else {
                System.out.println("false");
                errorList.add(dir.getName());
                failure++;
            }
            System.out.println("执行时间: "+ (etime-stime) +"ms");
            //预测个数
            sum++;
            time += etime-stime;
            ss += floats[0]*100;

        }
    }
}
