package com.visual.disease.core.base;

public interface ModelReturnDispose {
    /**
     * 将模型处理结果的概率变现
     * @param input
     * @return
     */
    float[] softmax(float[] input);

    /**
     * 对可信度进行排序
     * @param softmax
     * @return
     */
     float[] getFloats(float[] softmax);

    /**
     * 根据结果返回预测类型
     * @param output
     * @return
     */
    public String[] postprocess(float[] output);
}
