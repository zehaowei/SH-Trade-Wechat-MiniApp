package com.fantj.sbmybatis.model;

import io.swagger.annotations.ApiModelProperty;


public class Item {
    private Integer id;

    @ApiModelProperty(value="商品名称")
    private String name;

    @ApiModelProperty(value="商品图片URL")
    private String imgUrl;

    @ApiModelProperty(value="商品价格")
    private Double price;

    @ApiModelProperty(value="商品备注")
    private String notes;

    @ApiModelProperty(value="交易地址")
    private String address;

    @ApiModelProperty(value="出售人")
    private Integer seller;

    @ApiModelProperty(value="商品状态")
    private String status;

    @ApiModelProperty(value="商品发布日期")
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getSeller() {
        return seller;
    }

    public void setSeller(Integer seller) {
        this.seller = seller;
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

}