package com.smart.agriculture.Do;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author ylx
 * @since 2023-05-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MessagesList extends BaseDo {

    private static final long serialVersionUID=1L;

    /**
     * 发送端Username
     */
    @ApiModelProperty("发送端Username")
    private String sendNumber;

    /**
     * 发送者姓名
     */
    @ApiModelProperty("发送者姓名")
    private String sendName;

    /**
     * 接收端Username
     */
    @ApiModelProperty("接收端Username")
    private String acceptNumber;

    /**
     * 内容 
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 类型  （用户私信   系统通知   被评论回复等消息通知）
     *  10 系统通知
     *  20 点赞通知
     *  30 帖子回复通知
     *  40 评论回复通知
     */
    @ApiModelProperty("10 系统通知\n" +
            "20 点赞通知\n" +
            "30 帖子回复通知\n" +
            "40 评论回复通知")
    private Integer type;

    /**
     * 点赞数量
     */
    @ApiModelProperty("点赞数量")
    private Long likeNmb;

    /**
     * 点赞唯一标识符
     */
    @ApiModelProperty("点赞唯一标识符")
    private String otherId;

    /**
     * 状态 0--未读  1-- 已读
     */
    @ApiModelProperty("状态 0--未读  1-- 已读")
    private Boolean isRead;
}
