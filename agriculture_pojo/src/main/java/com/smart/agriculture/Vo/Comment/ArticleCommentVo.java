package com.smart.agriculture.Vo.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArticleCommentVo {
    /**
     * 评论数量
     */
    @ApiModelProperty("评论数量")
    private Integer CommentNumber;
    /**
     * 评论信息
     */
    @ApiModelProperty("评论信息")
    private List<CommentVo> commentVoList;
}
