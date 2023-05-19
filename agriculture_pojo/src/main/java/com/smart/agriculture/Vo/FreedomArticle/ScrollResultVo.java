package com.smart.agriculture.Vo.FreedomArticle;

import lombok.Data;

import java.util.List;

@Data
public class ScrollResultVo {
    private List<?> list;
    private Long minTime;
    private Integer offset;
}
