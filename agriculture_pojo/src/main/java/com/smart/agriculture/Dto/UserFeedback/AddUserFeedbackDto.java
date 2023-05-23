package com.smart.agriculture.Dto.UserFeedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AddUserFeedbackDto {


    /**
     * 问题主题
     */
    @ApiModelProperty("问题主题")
    private String title;

    /**
     * 问题详细
     */
    @ApiModelProperty("问题详细")
    private String content;
    /**
     * 联系方式
     */
    @ApiModelProperty("联系方式")
    private String contact;

    /**
     * 发生时间
     */
    @ApiModelProperty("发生时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime happenTime;

}
