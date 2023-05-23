package com.smart.agriculture.Dto.UserCollection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IsCollectionDto {
    /**
     * 目标id
     */
    @ApiModelProperty("目标id")
    private String collectionId;
    /**
     * 是否关注
     */
    @ApiModelProperty("是否关注")
    private Boolean isCollection;
}
