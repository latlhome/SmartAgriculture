package com.smart.agriculture.Vo.PlantingLog;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class GetPlantingLogListVo {
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
}
