package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Comment extends BaseDo {
    /**
     * 发布评论者Username
     */
    @ApiModelProperty("发布评论者Username")
    private String releaseUsername;
    /**
     * 被评论Code 评论为评论ID 文章为ID
     */
    @ApiModelProperty("被评论Code 评论为评论ID 文章为ID")
    private String beCommentedCode;
    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String content;
    /**
     * 被评论的类型 1.文章 2.评论
     */
    @ApiModelProperty("被评论的类型  1.文章 2.评论")
    private Integer beCommentedType;
}
