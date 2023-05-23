package com.smart.agriculture.Do;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ylx
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserCollection extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 收藏帖子Id
     */
    private String collectionId;

    @TableField(select = false)
    private Boolean isDel;


}
