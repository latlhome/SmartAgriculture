package com.smart.agriculture.Dto.UserFollow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IsFollowDto {
    /**
     * 目标Username
     */
    @ApiModelProperty("目标Username")
    private String followUserUsername;
    /**
     * 是否关注
     */
    @ApiModelProperty("是否关注")
    private Boolean isFollow;
}
