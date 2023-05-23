package com.smart.agriculture.Dto.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddCommentDto {
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
