package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseMenu extends BaseDo {
    /**
     * 所属类别
     */
    @ApiModelProperty("所属类别，0为类别 其他为植物所属类别ID")
    private String category;
    /**
     * 菜单类型，分为类别和植物 0 类别 1植物
     */
    @ApiModelProperty("菜单类型，分为类别和植物 0 类别 1植物")
    private String menuType;
    /**
     * 类别为类别名，植物为植物名
     */
    @ApiModelProperty("类别为类别名，植物为植物名")
    private String name;

    /**
     * 菜单示例图片
     */
    @ApiModelProperty("菜单示例图片")
    private String menuSamplePicture;

}
