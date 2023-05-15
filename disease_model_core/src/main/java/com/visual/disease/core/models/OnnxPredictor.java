// package com.visual.disease.core.models;
//
// import ai.onnxruntime.OnnxTensor;
// import ai.onnxruntime.OrtEnvironment;
// import ai.onnxruntime.OrtException;
// import ai.onnxruntime.OrtSession;
// import com.visual.disease.core.base.BaseOnnxInfer;
// import com.visual.disease.core.extract.DiseaseSearchModel;
// import com.visual.disease.core.utils.Util;
//
// import javax.imageio.ImageIO;
// import java.awt.*;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.util.Collections;
//
// public class OnnxPredictor extends BaseOnnxInfer implements DiseaseSearchModel {
//
//     static float sum = 0;
//     static float success = 0;
//     static float failure = 0;
//     static float ss = 0;
//     static float time = 0;
//     public OnnxPredictor(String modelPath, int threads) {
//         super(modelPath, threads);
//     }
//
//     // public void OnnxPredictor(String modelPath,int threads) throws OrtException {
//     //     // 创建 ONNX 运行时环境
//     //     this.env = OrtEnvironment.getEnvironment();
//     //     this.opts = new OrtSession.SessionOptions();
//     //     opts.setInterOpNumThreads(threads);
//     //     opts.setSessionLogLevel(OrtLoggingLevel.ORT_LOGGING_LEVEL_ERROR);
//     //     opts.setOptimizationLevel(OrtSession.SessionOptions.OptLevel.BASIC_OPT);
//     //     // 创建 ONNX 会话
//     //     this.session = env.createSession(modelPath, opts);
//     // }
//
//     /**
//      * 将张量进行评估
//      * @param transform
//      * @return
//      */
//     public float[] predict(OnnxTensor transform){
//
//         float[][] s;
//         OrtSession.Result output = null;
//         try {
//             output = getSession().run(Collections.singletonMap(getInputName(), transform));
//             s = (float[][]) output.get(0).getValue();
//         } catch (OrtException e) {
//             throw new RuntimeException(e);
//         }
//         return s[0];
//     }
//
//     public static void main(String[] args) throws Exception {
//
//         OnnxPredictor onnxPredictor = new OnnxPredictor("D:\\应用缓存文件\\QQ\\model.onnx",1);
//         // 加载图像
//         // BufferedImage img = ImageIO.read(new File("D:\\Study_need\\IMGS\\onnx\\1\\番茄叶斑病 (1).JPG"));
//         traverseDirectory(new File("D:\\Study_need\\IMGS\\onnx"));
//
//         System.out.println("预测个数: "+sum);
//         System.out.println("正确个数: "+success);
//         System.out.println("错误个数: "+failure);
//         System.out.println("预测平均置信度: "+ss/sum +"%");
//         System.out.println("预测准确率: "+success/sum*100 +"%");
//         System.out.println("单个图片预测时间: "+time/sum +"ms");
//
//         // 进行预测
//         // float[] output = onnxPredictor.predict(img);
//         // float[] softmax = com.visual.disease.core.utils.Util.softmax(output);
//         //
//         // float[] cls_score = getFloats(softmax);
//         //
//         // String postprocess = postprocess(softmax);
//         // System.out.println("Predicted class: " + postprocess);
//         // System.out.println("Predicted confidence: " + cls_score[0]);
//     }
//
//     private static float[] getFloats(float[] softmax) {
//         // 解析预测结果
//         int[] cls_index = new int[1];
//         float[] cls_score = new float[1];
//         for (int i = 0; i < 3; i++) {
//             if (softmax[i] > cls_score[0]) {
//                 cls_index[0] = i;
//                 cls_score[0] = softmax[i];
//             }
//         }
//         return cls_score;
//     }
//
//     /**
//      * 将图像的像素进行调整
//      * @param img
//      * @return
//      */
//     private BufferedImage preprocess(BufferedImage img){
//         int targetWidth = 224;
//         int targetHeight = 224;
//         BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
//         Graphics2D g = resized.createGraphics();
//         g.drawImage(img, 0, 0, targetWidth, targetHeight, null);
//         g.dispose();
//         return resized;
//     }
//
//     /**
//      * 将预处理后的数据转换为ONNX Tensor
//      * @param img
//      * @return
//      */
//     private OnnxTensor transform(BufferedImage img){
//         float[][][][] pixelData = new float[1][3][224][224];
//         // 获取图像的 RGB 像素值并进行归一化
//         for (int i = 0; i < 224; ++i) {
//             for (int j = 0; j < 224; ++j) {
//                 int rgb = img.getRGB(j, i);
//                 int r = (rgb >> 16) & 0xFF;
//                 int g = (rgb >> 8) & 0xFF;
//                 int b = (rgb & 0xFF);
//                 pixelData[0][0][i][j] = (float) (Math.round(Math.min(Math.max(((float)r / 255.0f - 0.4737f) / 0.1920f, 0), 1) * 10000) / 10000.0);
//                 pixelData[0][1][i][j] = (float) (Math.round(Math.min(Math.max(((float)g / 255.0f - 0.4948f) / 0.1592f, 0), 1) * 10000) / 10000.0);
//                 pixelData[0][2][i][j] = (float) (Math.round(Math.min(Math.max(((float)b / 255.0f - 0.4336f) / 0.2184f, 0), 1) * 10000) / 10000.0);
//
//             }
//         }
//
//         OnnxTensor inputTensor = null;
//         try {
//             inputTensor = OnnxTensor.createTensor(OrtEnvironment.getEnvironment(), pixelData);
//         } catch (OrtException e) {
//             throw new RuntimeException(e);
//         }
//         return inputTensor;
//     }
//
//     private static String postprocess(float[] output) {
//         int numClasses = output.length;
//         int maxIndex = 0;
//         float maxScore = Float.NEGATIVE_INFINITY;
//         for (int i = 0; i < numClasses; ++i) {
//             if (output[i] > maxScore) {
//                 maxScore = output[i];
//                 maxIndex = i;
//             }
//         }
//         // 这里假设您有一个预定义的类名列表，可以根据分类结果的索引查找对应的类名。
//         String[] classNames = {"番茄叶斑病", "苹果黑星病", "葡萄黑腐病"};
//         return classNames[maxIndex];
//     }
//
//     public static void traverseDirectory(File dir) throws Exception {
//         // OnnxPredictor onnxPredictor = new OnnxPredictor("D:\\应用缓存文件\\QQ\\model.onnx",1);
//         if (dir.isDirectory()) {  // 如果是目录，则遍历它
//             File[] files = dir.listFiles();  // 获取目录下所有文件和子目录
//             if (files != null) {
//                 for (File file : files) {
//                     traverseDirectory(file);  // 递归遍历目录下每个文件和子目录
//                 }
//             }
//         } else {  // 如果不是目录，则处理它
//             // 处理文件的代码，例如：
//             BufferedImage img = ImageIO.read(dir);
//             // 开始时间
//             long stime = System.currentTimeMillis();
//             // 进行预测
//             float[] output = predict(img);
//             float[] softmax = Util.softmax(output);
//
//             float[] cls_score = getFloats(softmax);
//
//             String postprocess = postprocess(softmax);
//             // 结束时间
//             long etime = System.currentTimeMillis();
//
//             System.out.println("---------------------------------");
//             System.out.println("预测类型: " + postprocess);
//             System.out.println("可信度: " + cls_score[0]*100 +"%");
//             if (dir.getName().substring(0,5).equals(postprocess)){
//                 System.out.println("ture");
//                 success++;
//             }else {
//                 System.out.println("false");
//                 failure++;
//             }
//             System.out.println("执行时间: "+ (etime-stime) +"ms");
//             //预测个数
//             sum++;
//             time += etime-stime;
//             ss += cls_score[0]*100;
//         }
//     }
//
// }