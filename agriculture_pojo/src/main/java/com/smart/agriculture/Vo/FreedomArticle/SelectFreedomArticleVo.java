package com.smart.agriculture.Vo.FreedomArticle;

import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import lombok.Data;

import java.util.List;
@Data
public class SelectFreedomArticleVo {

    /**
     * id
     */
    private String id;

    /**
     * 发布者信息
     */
    private SysUserArticleVo userArticleVo;

    /**
     * 对应农作物编号
     */
    private String plantId;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 配图 多个图片用#间隔
     */
    private List<String> drawings;

    /**
     * 点赞数量
     */
    private Integer liked;
    /**
     * 是否点赞
     */
    private Boolean isLike;
    /**
     * 是否收藏
     */
    private Boolean isCollect;

}
