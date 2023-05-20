package com.smart.agriculture.Dto.SysPermission;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 权限ID
 */
@Data
@ApiModel("SysPermissionBanRestart :: 权限删除")
public class SysPermissionBanRestartDto {

    @ApiModelProperty("权限ID")
    @NotEmpty(message = "权限ID不能为空")
    private List<Long> permissionId;
}
