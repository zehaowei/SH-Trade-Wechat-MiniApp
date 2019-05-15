package com.shtrade.tradeservice.entity;

public class OrderDetail {

    private int id;

    private UserDetail seller;

    private UserDetail buyer;

    private String status;

    private String date;

    private ItemPublish itemPublish;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDetail getSeller() {
        return seller;
    }

    public void setSeller(UserDetail seller) {
        this.seller = seller;
    }

    public UserDetail getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDetail buyer) {
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

    public ItemPublish getItemPublish() {
        return itemPublish;
    }

    public void setItemPublish(ItemPublish itemPublish) {
        this.itemPublish = itemPublish;
    }
}
