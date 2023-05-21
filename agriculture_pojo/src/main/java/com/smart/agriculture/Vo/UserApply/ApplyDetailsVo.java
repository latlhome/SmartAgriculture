package com.smart.agriculture.Vo.UserApply;

import com.smart.agriculture.Vo.UserApplyFlow.ApplyFlowsVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class ApplyDetailsVo {

    /**
     * 申请到身份编号
     */
    @ApiModelProperty("申请到身份编号")
    private String applyRoleNumber;
    /**
     * 申请依据 文本
     */
    @ApiModelProperty("申请依据 文本")
    private String applyBasis;
    /**
     * 申请依据 图片 多个图片用#间隔
     */
    @ApiModelProperty("申请依据 图片 多个图片用#间隔")
    private String applyBasisPicture;

    /**
     * 是否同意  -2退回修改 -1拒绝 0 待审核 1同意
     */
    @ApiModelProperty("是否同意  -2退回修改 -1拒绝 0 待审核 1同意")
    private Integer isAgree;
    /**
     * 审批流程
     */
    private List<ApplyFlowsVo> applyFlows;

}
