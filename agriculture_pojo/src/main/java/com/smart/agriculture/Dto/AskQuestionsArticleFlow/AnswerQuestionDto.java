package com.smart.agriculture.Dto.AskQuestionsArticleFlow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnswerQuestionDto {
    /**
     * 对应文章id
     */
    @ApiModelProperty("对应文章id")
    private Long articleId;
    /**
     * 回答
     */
    @ApiModelProperty("回答")
    private String answer;

}
