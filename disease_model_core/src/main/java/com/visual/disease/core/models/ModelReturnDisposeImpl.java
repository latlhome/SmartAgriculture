package com.visual.disease.core.models;

import com.visual.disease.core.base.BaseOnnxInfer;
import com.visual.disease.core.base.ModelReturnDispose;

import java.util.Arrays;

public class ModelReturnDisposeImpl extends BaseOnnxInfer implements ModelReturnDispose {
    /**
     * 构造函数
     *
     * @param modelPath
     * @param threads
     */
    public ModelReturnDisposeImpl(String modelPath, int threads) {
        super(modelPath, threads);
    }

    /**
     * 将模型处理结果的概率变现
     * @param input
     * @return
     */
    @Override
    public float[] softmax(float[] input) {
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

    /**
     * 对可信度进行排序
     * @param softmax
     * @return
     */
    public float[] getFloats1(float[] softmax) {
        // 解析预测结果
        int[] cls_index = new int[1];
        float[] cls_score = new float[1];
        for (int i = 0; i < 3; i++) {
            if (softmax[i] > cls_score[0]) {
                cls_index[0] = i;
                cls_score[0] = softmax[i];
            }
        }
        return cls_score;
    }
    @Override
    public float[] getFloats(float[] softmax) {
        Arrays.sort(softmax);
        float[] sorted = new float[softmax.length];
        for (int i = 0; i < softmax.length; i++) {
            sorted[i] = softmax[softmax.length - i - 1];
        }
        return sorted;
    }

    /**
     * 根据结果返回预测类型
     * @param output 模型处理结果
     * @return 预测one
     */
    // @Override
    public String postprocess1(float[] output) {
        int numClasses = output.length;
        int maxIndex = 0;
        float maxScore = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < numClasses; ++i) {
            if (output[i] > maxScore) {
                maxScore = output[i];
                maxIndex = i;
            }
        }
        // 这里假设您有一个预定义的类名列表，可以根据分类结果的索引查找对应的类名。
        String[] classNames = {"番茄叶斑病", "苹果黑星病", "葡萄黑腐病"};
        return classNames[maxIndex];
    }
    @Override
    public String[] postprocess(float[] output) {
        String[] classNames = {"番茄叶斑病", "苹果黑星病", "葡萄黑腐病"};
        int numClasses = output.length;
        // 创建一个索引数组，用于排序。
        Integer[] indexes = new Integer[numClasses];
        for (int i = 0; i < numClasses; ++i) {
            indexes[i] = i;
        }
        // 按输出向量中的分数进行排序。
        Arrays.sort(indexes, (a, b) -> Float.compare(output[b], output[a]));

        // 根据排序的顺序创建新的类名数组。
        String[] sortedClassNames = new String[numClasses];
        for (int i = 0; i < numClasses; ++i) {
            sortedClassNames[i] = classNames[indexes[i]];
        }

        return sortedClassNames;
    }
}
