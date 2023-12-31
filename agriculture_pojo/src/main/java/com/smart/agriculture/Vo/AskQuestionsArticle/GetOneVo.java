package com.smart.agriculture.Vo.AskQuestionsArticle;

import com.smart.agriculture.Vo.SysUser.SysUserArticleVo;
import lombok.Data;

import java.util.List;

@Data
public class GetOneVo {
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
    private List<String> drawing;


    /**
     * 问题状态 -1等待回答 0待处理 1已解决
     */
    private Integer state;
}
