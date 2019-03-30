package com.shtrade.tradeservice.entity;

import io.swagger.annotations.ApiModelProperty;

public class ItemPublish extends ItemBrief{

    @ApiModelProperty(value="交易地址")
    private String address;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

}
