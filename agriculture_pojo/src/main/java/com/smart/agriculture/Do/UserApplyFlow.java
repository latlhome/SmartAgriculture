package com.smart.agriculture.Do;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ylx
 * @since 2023-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserApplyFlow extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 申请Id
     */
    private String applyId;

    /**
     * 审批人Username
     */
    private String approvalUsername;

    /**
     * 审批状态 -2退回修改 -1拒绝 1同意
     */
    private Integer approvalState;

    /**
     * 审批意见
     */
    private String approvalOpinion;


}
