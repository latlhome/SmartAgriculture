package com.smart.agriculture.Dto.UserFeedback;

import com.smart.agriculture.Dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserFeedbackListDto extends PageDto {
    /**
     * 状态
     */
    @ApiModelProperty("查询状态")
    private Integer state;
}
