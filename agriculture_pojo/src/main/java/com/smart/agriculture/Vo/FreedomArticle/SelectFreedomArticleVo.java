package com.smart.agriculture.Vo.FreedomArticle;

import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class SelectFreedomArticleVo {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 发布者信息
     */
    @ApiModelProperty("发布者信息")
    private SysUserArticleVo userArticleVo;

    /**
     * 对应农作物编号
     */
    @ApiModelProperty("对应农作物编号")
    private String plantId;
    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String content;
    /**
     * 配图 多个图片用#间隔
     */
    @ApiModelProperty("配图 多个图片用#间隔")
    private List<String> drawings;

    /**
     * 点赞数量
     */
    @ApiModelProperty("点赞数量")
    private Integer liked;
    // /**
    //  * 评论数量
    //  */
    // @ApiModelProperty("评论数量")
    // private Integer commentNumber;
    // /**
    //  * 评论信息
    //  */
    // @ApiModelProperty("评论信息")
    // private ArticleCommentVo articleCommentVo;
    /**
     * 是否点赞
     */
    @ApiModelProperty("是否点赞")
    private Boolean isLike;
    /**
     * 是否收藏
     */
    @ApiModelProperty("是否收藏")
    private Boolean isCollect;

}
