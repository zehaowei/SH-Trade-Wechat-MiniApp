package com.shtrade.tradeservice.entity;

import io.swagger.annotations.ApiModelProperty;

public class ItemPost extends ItemBrief {

    @ApiModelProperty(value="商品状态")
    private String status;

    @ApiModelProperty(value="商品发布日期")
    private String date;

    @ApiModelProperty(value="交易地址")
    private String address;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}
