package com.smart.agriculture.Vo.MessagesList;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MySystemMessageListVo {
    /**
     * id
     */
    private Long id;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;

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
