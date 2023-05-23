package com.smart.agriculture.Dto.UserFeedback;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class UpdateUserFeedbackDto {
    /**
     * id
     */
    private Long id;

    /**
     * 问题状态 -1未处理 0正在解决 1 已解决
     */
    @ApiModelProperty("问题状态 -1未处理 0正在解决 1已解决")
    private Integer state;

    /**
     * 开发者回复
     */
    @ApiModelProperty("开发者回复")
    private String answer;

}
