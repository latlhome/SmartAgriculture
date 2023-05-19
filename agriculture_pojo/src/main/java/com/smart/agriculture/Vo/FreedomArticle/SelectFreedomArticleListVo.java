package com.smart.agriculture.Vo.FreedomArticle;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SelectFreedomArticleListVo {

    /**
     * 帖子id
     */
    @ApiModelProperty("帖子id")
    private String id;
    /**
     * 对应农作物编号
     */
    @ApiModelProperty("对应农作物编号")
    private String plantId;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 点赞数量
     */
    @ApiModelProperty("点赞数量")
    private Long liked;

    /**
     * 配图
     */
    @ApiModelProperty("配图 ")
    private List<String> drawing;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
