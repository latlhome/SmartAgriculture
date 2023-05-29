package com.smart.agriculture.Vo.AskQuestionsArticleFlow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import lombok.Data;

import java.util.Date;

@Data
public class GetAnswerByAskIdVo {
    /**
     * id
     */
    private String id;
    /**
     * 发布者信息
     */
    private SysUserArticleVo userArticleVo;
    /**
     * 问题回复
     */
    private String answer;
    /**
     * 是否采纳 -1否 0待处理 1是
     */
    private Integer adopt;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
