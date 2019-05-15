package com.fantj.sbmybatis.model;

import io.swagger.annotations.ApiModelProperty;

public class Order {
    private Integer id;

    @ApiModelProperty(value="卖家id")
    private Integer seller;

    @ApiModelProperty(value="买家id")
    private Integer buyer;

    @ApiModelProperty(value="订单状态")
    private String status;

    @ApiModelProperty(value="订单生成日期")
    private String date;

    @ApiModelProperty(value="商品id")
    private Integer itemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeller() {
        return seller;
    }

    public void setSeller(Integer seller) {
        this.seller = seller;
    }

    public Integer getBuyer() {
        return buyer;
    }

    public void setBuyer(Integer buyer) {
        this.buyer = buyer;
    }

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

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}