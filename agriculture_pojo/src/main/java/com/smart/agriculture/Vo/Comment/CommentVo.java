package com.smart.agriculture.Vo.Comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommentVo {

    /**
     * 评论id
     */
    private String id;

    /**
     * 评论人username
     */
    private String commentUserUsername;
    /**
     * 评论人昵称
     */
    private String commentUserNickname;
    /**
     * 评论人头像
     */
    private String commentUserPicture;
    /**
     * 是否拥有者
     */
    private boolean owner = false;
    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论数量
     */
    private Integer commentNumber;

    /**
     * 评论时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

}
