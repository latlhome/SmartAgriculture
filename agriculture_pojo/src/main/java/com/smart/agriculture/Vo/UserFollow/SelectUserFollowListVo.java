package com.smart.agriculture.Vo.UserFollow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SelectUserFollowListVo {
    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户头像地址
     */
    @ApiModelProperty("用户头像地址")

    private String headPicture;
}
