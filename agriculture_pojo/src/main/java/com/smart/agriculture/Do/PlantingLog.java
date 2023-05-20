package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 种植日志
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PlantingLog extends BaseDo{

    /**
     * 作者Username
     */
    @ApiModelProperty("作者Username")
    private String AuthorUsername;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
    /**
     * 配图 多个图片用#间隔
     */
    @ApiModelProperty("配图 多个图片用#间隔")
    private String drawing;
}
