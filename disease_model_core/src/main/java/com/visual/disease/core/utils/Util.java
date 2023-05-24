package com.visual.disease.core.utils;

import ai.onnxruntime.OnnxTensor;
import com.visual.disease.core.base.BaseOnnxInfer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

public class Util extends BaseOnnxInfer {
    /**
     * 构造函数
     *
     * @param modelPath
     * @param threads
     */
    public Util(String modelPath, int threads) {
        super(modelPath, threads);
    }

    /**
     * 将BufferedImage转换为本地图像
     *
     * @param image
     * @param filename
     */
    public static void bufferedImageToImage(BufferedImage image, String filename) {
        try {
            // 将缓冲图片保存到本地文件中
            ImageIO.write(image, "png", new File(filename));
        } catch (Exception e) {
            throw new IllegalArgumentException("不能将图像保存到本地：" + e.getMessage());
        }
    }

    /**
     * 将OnnxTensor对象转换为本地图像
     *
     * @param imageTensor
     * @param filename
     * @throws Exception
     */
    public static void onnxTensorToImage(OnnxTensor imageTensor, String filename) throws Exception {
        // 获取图像张量的维度信息
        long[] shape = imageTensor.getInfo().getShape();

        // 检查图像张量维度是否正确
        if (shape.length != 4 || shape[0] != 1) {
            throw new IllegalArgumentException("输入张量必须为（1，C，H，W）的四维张量");
        }

        // 获取图像张量的宽、高和通道数
        int height = Math.toIntExact(shape[2]);
        int width = Math.toIntExact(shape[3]);
        int channels = Math.toIntExact(shape[1]);

        // 获取张量中的数据，并创建缓冲图片
        float[][][][] imageData = (float[][][][]) imageTensor.getValue();
        float[] flatData = flatten(imageData);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 将张量中的数据存储到缓冲图片中
        for (int h = 0; h < height; ++h) {
            for (int w = 0; w < width; ++w) {
                int pixelValue = 0;
                for (int c = 0; c < channels; ++c) {
                    int index = c * height * width + h * width + w;
                    float channelValue = flatData[index];
                    switch (c) {
                        case 0:
                            channelValue = channelValue * 0.1920f + 0.4737f;
                            break;
                        case 1:
                            channelValue = channelValue * 0.1592f + 0.4948f;
                            break;
                        case 2:
                            channelValue = channelValue * 0.2184f + 0.4336f;
                            break;
                        default:
                            throw new IllegalArgumentException("不支持的通道数：" + channels);
                    }
                    channelValue = Math.min(Math.max(channelValue, 0.0f), 1.0f); // 将数据的范围限制到[0,1]之间
                    int channelPixel = Math.round(channelValue * 255.0f);
                    pixelValue |= channelPixel << ((2 - c) * 8);
                }
                image.setRGB(w, h, pixelValue);
            }
        }

        // 将缓冲图片保存到本地文件中
        try {
            ImageIO.write(image, "png", new File(filename));
        } catch (Exception e) {
            throw new IllegalArgumentException("不能将图像保存到本地：" + e.getMessage());
        }
    }

    /**
     * 四维数组转一维
     *
     * @param data
     * @return
     */
    public static float[] flatten(float[][][][] data) {
        final int batchSize = data.length;
        final int channels = data[0].length;
        final int height = data[0][0].length;
        final int width = data[0][0][0].length;

        float[] flatData = new float[batchSize * channels * height * width];
        int index = 0;
        for (int b = 0; b < batchSize; ++b) {
            for (int c = 0; c < channels; ++c) {
                for (int h = 0; h < height; ++h) {
                    for (int w = 0; w < width; ++w) {
                        flatData[index++] = data[b][c][h][w];
                    }
                }
            }
        }

        return flatData;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = resizedImage.createGraphics();
        AffineTransform transform = AffineTransform.getScaleInstance(
                (double) newWidth / originalImage.getWidth(),
                (double) newHeight / originalImage.getHeight()
        );
        g.drawRenderedImage(originalImage, transform);
        g.dispose();
        return resizedImage;
    }


    public static void saveNormalizedTensorAsImage(OnnxTensor tensor, String filePath) throws Exception {
        float[][][][] pixelData = (float[][][][]) tensor.getValue();
        int height = pixelData[0][0].length;
        int width = pixelData[0][0][0].length;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                int r = (int) (pixelData[0][0][y][x] * 255.0f + 0.5f);
                int g = (int) (pixelData[0][1][y][x] * 255.0f + 0.5f);
                int b = (int) (pixelData[0][2][y][x] * 255.0f + 0.5f);
                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);
                int rgb = (r << 16) | (g << 8) | b;
                img.setRGB(x, y, rgb);
            }
        }
        ImageIO.write(img, "png", new File(filePath));
    }

    public static float[] softmax(float[] input) {
        float sumExp = 0f;
        float[] output = new float[input.length];

        // Compute the sum of the exponentials of input values
        for (int i = 0; i < input.length; i++) {
            sumExp += Math.exp(input[i]);
        }

        // Compute softmax probabilities for each input value
        for (int i = 0; i < input.length; i++) {
            output[i] = (float) Math.exp(input[i]) / sumExp;
        }

        return output;
    }


}