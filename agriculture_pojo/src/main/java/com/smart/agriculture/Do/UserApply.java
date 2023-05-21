package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
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
public class UserApply extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 申请前的身份
     */
    @ApiModelProperty("申请前的身份")
    private String nowRole;

    /**
     * 申请者用户名
     */
    @ApiModelProperty("申请者用户名")
    private String username;

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


}
