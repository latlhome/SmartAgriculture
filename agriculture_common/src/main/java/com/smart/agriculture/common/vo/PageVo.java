package com.smart.agriculture.common.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhuzhixu
 */
@Data
@ApiModel
public class PageVo<T> {
    @ApiModelProperty(value = "当前页码", required = true)
    private Long currentPage;
    @ApiModelProperty(value = "每页记录数", required = true)
    private Long pageSize;
    @ApiModelProperty(value = "查询条件", required = false)
    private T filter;
    @ApiModelProperty(value = "查询数据记录起始位置,不需要传参", required = false)
    @JsonIgnore
    private Long initData;

    /**
     * 计算第一条数据
     * 使用mapper.xml自己书写sql时,插入对象预先执行该方法获取limit数据起始位
     */
    public void calStartData() {
        if (this.currentPage.equals(0L)){
            this.initData = 0L;
            return;
        }
        this.initData = (this.currentPage - 1) * this.pageSize;
    }
}
