package com.shtrade.tradeservice.entity;

import io.swagger.annotations.ApiModelProperty;

public class ItemDetail extends ItemPublish {

    @ApiModelProperty(value="出售人")
    private Integer seller;


    public Integer getSeller() {
        return seller;
    }

    public void setSeller(Integer seller) {
        this.seller = seller;
    }
}
