package com.smart.agriculture.Dto.SysPermission;

import com.smart.agriculture.Dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SelectPermissionListDto extends PageDto {
    /**
     * 模糊查询
     */
    @ApiModelProperty("模糊查询")
    private String label;
}
