package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 问答文章流程
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskQuestionsArticleFlow {
    /**
     * 文章ID
     */
    @ApiModelProperty("文章ID")
    private String ArticleId;
    /**
     * 回答人Id
     */
    @ApiModelProperty("回答人Id")
    private String answerUserId;
    /**
     * 问题回复
     */
    @ApiModelProperty("问题回复")
    private String answer;
    /**
     * 是否采纳 0否 1是
     */
    @ApiModelProperty("是否采纳 0否 1是")
    private Integer adopt;

}
