package com.smart.agriculture.Vo.Comment;

import lombok.Data;

@Data
public class SecondaryCommentVo {
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
     * 评论内容
     */
    private String commentContent;
}
