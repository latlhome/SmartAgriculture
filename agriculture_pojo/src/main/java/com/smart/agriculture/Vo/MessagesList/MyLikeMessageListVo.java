package com.smart.agriculture.Vo.MessagesList;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class MyLikeMessageListVo {
    /**
     * 点赞数量
     */
    @ApiModelProperty("点赞数量")
    private Long likeNmb;

    /**
     * 点赞唯一标识符
     */
    @ApiModelProperty("点赞唯一标识符")
    private String otherId;
    @ApiModelProperty("被回复的信息 帖子为帖子名，回复为字段")
    private String data;

    /**
     * 状态 0--未读  1-- 已读
     */
    @ApiModelProperty("状态 0--未读  1-- 已读")
    private Boolean isRead;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
