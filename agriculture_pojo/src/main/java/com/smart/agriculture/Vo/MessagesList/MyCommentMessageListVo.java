package com.smart.agriculture.Vo.MessagesList;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class MyCommentMessageListVo {
    /**
     * 发送者相关信息
     */
    @ApiModelProperty("发送者相关信息")
    private SysUserArticleVo sysUserArticleVo;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 类型  （用户私信   系统通知   被评论回复等消息通知）
     *  10 系统通知
     *  20 点赞通知
     *  30 帖子回复通知
     *  40 评论回复通知
     */
    @ApiModelProperty("30 帖子回复通知 40 评论回复通知")
    private Integer type;
    /**
     * 回复的相关内容
     */
    @ApiModelProperty("相关内容")
    private String data;

    /**
     * 点赞唯一标识符
     */
    @ApiModelProperty("点赞唯一标识符")
    private String otherId;

    /**
     * 状态 0--未读  1-- 已读
     */
    @ApiModelProperty("状态 0--未读  1-- 已读")
    private Boolean isRead;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
