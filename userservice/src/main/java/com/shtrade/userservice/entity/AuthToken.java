package com.shtrade.userservice.entity;

import io.swagger.annotations.ApiModelProperty;

public class AuthToken {

    @ApiModelProperty("用户ID")
    private int userId;

    @ApiModelProperty("随机Token")
    private String token;

    public AuthToken(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
