package com.smart.agriculture.Vo.AskQuestionsArticle;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class GetAllListVo {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
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
     * 配图 多个图片用#间隔
     */
    @ApiModelProperty("配图 多个图片用#间隔")
    private List<String> drawing;

    /**
     * 问题状态 -1等待回答 0待处理 1已解决
     */
    @ApiModelProperty("问题状态 -1等待回答 0待处理 1已解决")
    private Integer state;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}