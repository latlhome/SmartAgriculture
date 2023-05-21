package com.smart.agriculture.Dto.UserApplyFlow;

import lombok.Data;

@Data
public class ApproveDto {
    /**
     * 申请Id
     */
    private String applyId;

    /**
     * 审批状态 -2退回修改 -1拒绝 1同意
     */
    private Integer approvalState;

    /**
     * 审批意见
     */
    private String approvalOpinion;
}
