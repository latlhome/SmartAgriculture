package com.smart.agriculture.Dto.UserApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateUserApplyDto {
    /**
     * id
     */
    private Long id;
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
}
