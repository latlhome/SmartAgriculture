package com.smart.agriculture.Vo.SysUser;

import lombok.Data;

@Data
public class SysUserArticleVo {
    /**
     * 作者Username
     */
    private String authorUsername;
    /**
     * 作者头像
     */
    private String authorPicture;
    /**
     * 作者昵称
     */
    private String authorNickname;
}
