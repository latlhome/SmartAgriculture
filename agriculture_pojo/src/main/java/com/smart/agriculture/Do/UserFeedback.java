package com.smart.agriculture.Do;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author ylx
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserFeedback extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 反馈人的Username
     */
    @ApiModelProperty("反馈人的Username")
    private String username;

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
    /**
     * 问题状态 -1未处理 0正在解决 1 已解决
     */
    @ApiModelProperty("问题状态 -1未处理 0正在解决 1 已解决")
    private Integer state;

    /**
     * 开发者回复
     */
    @ApiModelProperty("开发者回复")
    private String answer;


}
