package com.visual.disease.core.domain;

import io.swagger.annotations.ApiModelProperty;

public class Output {
    public Output() {
    }
    /**
     * 病害id
     */
    @ApiModelProperty("病害id")
    private String id;
    /**
     * 病害简介
     */
    @ApiModelProperty("病害简介")
    private String introduction;

    /**
     * 预测类型
     */
    @ApiModelProperty("预测类型")
    private String postprocess;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 可信度
     */
    @ApiModelProperty("可信度")
    private float cls_score;

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


}
