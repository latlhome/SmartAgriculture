package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 问答文章
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskQuestionsArticle extends BaseDo {

    /**
     * 作者ID
     */
    @ApiModelProperty("作者ID")
    private String authorId;
    /**
     * 对应农作物编号
     */
    @ApiModelProperty("对应农作物编号")
    private String plantId;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
    /**
     * 配图 多个图片用#间隔
     */
    @ApiModelProperty("配图 多个图片用#间隔")
    private String drawing;


    /**
     * 问题状态 -1未解决 0待处理 1已解决
     */
    @ApiModelProperty("问题状态 -1未解决 0待处理 1已解决")
    private String state;
}
