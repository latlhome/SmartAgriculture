package com.smart.agriculture.Vo.DiseaseMenu;

import lombok.Data;

@Data
public class GetCategoryVo {
    /**
     * 类别ID
     */
    private String id;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 类别对应图片
     */
    private String menuSamplePicture;
}
