package com.visual.disease.core.domain;

public class Output {
    public Output() {
    }

    /**
     * 预测类型
     */
    private String postprocess;

    public Output(String postprocess, float cls_score) {
        this.postprocess = postprocess;
        this.cls_score = cls_score;
    }

    public String getPostprocess() {
        return postprocess;
    }

    public void setPostprocess(String postprocess) {
        this.postprocess = postprocess;
    }

    public float getCls_score() {
        return cls_score;
    }

    public void setCls_score(float cls_score) {
        this.cls_score = cls_score;
    }

    /**
     * 可信度
     */
    private float cls_score;

}
