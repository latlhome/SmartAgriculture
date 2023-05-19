package com.smart.agriculture.Dto.SysUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChangeInformationDto {


    /**
     * 真实姓名
     */
    @ApiModelProperty("用户真实姓名")
    private String name;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户头像地址
     */
    @ApiModelProperty("用户头像地址")
    private String headPicture;


    /**
     * 0保密1男2女
     */
    @ApiModelProperty("性别 0保密1男2女")
    private Integer sex;

    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String phone;

}
