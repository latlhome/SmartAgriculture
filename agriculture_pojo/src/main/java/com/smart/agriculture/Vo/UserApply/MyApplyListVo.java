package com.smart.agriculture.Vo.UserApply;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MyApplyListVo {
    /**
     * 申请id
     */
    private String id;
    /**
     * 申请到身份编号
     */
    private String applyRoleNumber;
    /**
     * 申请依据 文本
     */
    private String applyBasis;
    /**
     * 申请依据 图片 多个图片用#间隔
     */
    private String applyBasisPicture;

    /**
     * 是否同意  -2退回修改 -1拒绝 0 待审核 1同意
     */
    private Integer isAgree;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
