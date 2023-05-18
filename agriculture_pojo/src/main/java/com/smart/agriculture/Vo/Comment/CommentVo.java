package com.smart.agriculture.Vo.Comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentVo {

    /**
     * 评论人username
     */
    @ApiModelProperty("评论人username")
    private String CommentUserUsername;

    /**
     * 评论人昵称
     */
    @ApiModelProperty("评论人昵称")
    private String CommentUserNickname;
    /**
     * 评论人头像
     */
    @ApiModelProperty("评论人头像")
    private String CommentUserPicture;

    /**
     * 评论数量
     */
    @ApiModelProperty("评论数量")
    private Integer CommentNumber;



}
