package com.shtrade.tradeservice.entity;

import io.swagger.annotations.ApiModelProperty;

public class ItemBrief extends ItemBase{

    @ApiModelProperty(value="商品名称", required = true)
    private String name;

    @ApiModelProperty(value="商品图片URL", required = true)
    private String imgUrl;

    @ApiModelProperty(value="商品价格", required = true)
    private Integer price;

    @ApiModelProperty(value="商品备注")
    private String notes;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

}
