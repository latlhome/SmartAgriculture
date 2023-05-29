package com.smart.agriculture.Vo.MessagesList;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class MyLikeMessageListVo {
    /**
     * 点赞数量
     */
    private Long likeNmb;

    /**
     * 点赞唯一标识符
     */
    private String otherId;
    private String data;

    /**
     * 状态 0--未读  1-- 已读
     */
    private Boolean isRead;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
