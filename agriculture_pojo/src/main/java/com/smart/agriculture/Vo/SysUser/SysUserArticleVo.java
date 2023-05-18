package com.smart.agriculture.Vo.SysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserArticleVo {
    /**
     * 作者Username
     */
    @ApiModelProperty("作者Username")
    private String authorUsername;
    /**
     * 作者头像
     */
    @ApiModelProperty("作者头像")
    private String authorPicture;
    /**
     * 作者昵称
     */
    @ApiModelProperty("作者昵称")
    private String authorNickname;
}
