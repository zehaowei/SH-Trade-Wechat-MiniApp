package com.shtrade.tradeservice.entity;

public class UserDetail extends User{

    private String identifier;

    public UserDetail(User user) {
        super();
        this.setId(user.getId());
        this.setAvatar(user.getAvatar());
        this.setNickname(user.getNickname());
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
