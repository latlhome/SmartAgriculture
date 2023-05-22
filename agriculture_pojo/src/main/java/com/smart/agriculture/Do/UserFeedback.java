package com.smart.agriculture.Do;

import com.smart.agriculture.Do.BaseDo;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class UserFeedback extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 反馈人的Username
     */
    private String username;

    /**
     * 问题主题
     */
    private String title;

    /**
     * 问题详细
     */
    private String content;

    /**
     * 发生时间
     */
    private LocalDateTime happenTime;

    /**
     * 问题状态 -1未处理 0正在解决 1 已解决
     */
    private Integer state;

    /**
     * 开发者回复
     */
    private String answer;


}
