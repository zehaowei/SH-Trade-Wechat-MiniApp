package com.shtrade.tradeservice.entity;

import io.swagger.annotations.ApiModelProperty;

public class ItemBase {

    @ApiModelProperty(value="商品ID，请求时无需提供，仅供返回")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
