package com.shtrade.userservice.entity;

public class AuthToken {
    //用户id
    private int userId;

    //随机生成的uuid
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
