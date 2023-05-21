package com.smart.agriculture.Vo.UserApplyFlow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class ApplyFlowsVo {
    /**
     * 审批状态 -2退回修改 -1拒绝 1同意
     */
    private Integer approvalState;

    /**
     * 审批意见
     */
    private String approvalOpinion;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
