package com.smart.agriculture.Dto;

import lombok.Data;

/**
 * @author zfm
 * @date 2022/6/24 14:31
 */
@Data
public class PageDto {
    private Long pageNum = 1L;

    private Long pageSize = 10L;
}
