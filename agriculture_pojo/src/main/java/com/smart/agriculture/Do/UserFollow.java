package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ylx
 * @since 2023-05-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserFollow extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 被关注用户名
     */
    @ApiModelProperty("被关注用户名")
    private String followUserId;


}
