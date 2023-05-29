package com.smart.agriculture.Vo.UserFeedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserFeedbackVo {

    private static final long serialVersionUID=1L;

    /**
     * 发布者信息
     */
    private SysUserArticleVo userArticleVo;

    /**
     * 问题主题
     */
    private String title;

    /**
     * 问题详细
     */
    private String content;
    /**
     * 联系方式
     */
    private String contact;

    /**
     * 发生时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime happenTime;

    /**
     * 问题状态 -1未处理 0正在解决 1 已解决
     */
    private Integer state;

    /**
     * 开发者回复
     */
    private String answer;

}
