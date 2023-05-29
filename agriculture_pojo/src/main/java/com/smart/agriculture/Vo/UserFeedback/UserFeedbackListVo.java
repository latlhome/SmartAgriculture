package com.smart.agriculture.Vo.UserFeedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class UserFeedbackListVo {
    /**
     * id
     */
    private String id;

    /**
     * 问题主题
     */
    private String title;


    /**
     * 问题状态 -1未处理 0正在解决 1 已解决
     */
    private Integer state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
