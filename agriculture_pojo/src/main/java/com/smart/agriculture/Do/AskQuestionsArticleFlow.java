package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 问答文章流程
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AskQuestionsArticleFlow extends BaseDo {
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
