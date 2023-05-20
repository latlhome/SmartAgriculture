package com.smart.agriculture.Dto.SysPermission;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限更新
 */
@Data
@ApiModel("SysPermissionUpdate :: 用户权限更新")
public class SysUserPermissionUpdateDto {

    @NotNull(message = "用户username不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty("用户username")
    private String username;

    @ApiModelProperty("权限码")
    @NotNull(message = "权限码不能为空")
    private List<String> authList;

    /**
     * 是否马上生效
     */
    @ApiModelProperty("是否马上生效")
    private Boolean ifFast = false;
}
