package com.smart.agriculture.Dto.FreedomArticle;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用于关注别人时，关注推文列表里面Dto
 */
@Data
public class AddAllArticleIdDto {
    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
