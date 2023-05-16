package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * role
 * </p>
 *
 * @author ylx
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 角色中文名称 角色中文名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 权限码 权限编码
     */
    @ApiModelProperty("权限码 权限编码")
    private String authData;

    /**
     * 对应的typeNumber
     */
    @ApiModelProperty("对应的typeNumber")
    private Integer typeNumber;


}
