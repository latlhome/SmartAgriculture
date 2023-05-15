package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseDo {
    /**
     * 发布评论者ID
     */
    @ApiModelProperty("发布评论者ID")
    private String releaseUserId;
    /**
     * 被评论ID
     */
    @ApiModelProperty("被评论ID")
    private String beCommentedId;
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
