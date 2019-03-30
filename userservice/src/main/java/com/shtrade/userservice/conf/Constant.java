package com.shtrade.userservice.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "constant")
@PropertySource("constant.properties")
public class Constant {

    private String identity_type_username;
    private String identity_type_wechat;

    private String user_avatar_default;

    private long token_expires_time;

    private String header_key_token;
    private String header_key_current_userid;

    public String getIdentity_type_username() {
        return identity_type_username;
    }

    public void setIdentity_type_username(String identity_type_username) {
        this.identity_type_username = identity_type_username;
    }

    public String getIdentity_type_wechat() {
        return identity_type_wechat;
    }

    public void setIdentity_type_wechat(String identity_type_wechat) {
        this.identity_type_wechat = identity_type_wechat;
    }

    public String getUser_avatar_default() {
        return user_avatar_default;
    }

    public void setUser_avatar_default(String user_avatar_default) {
        this.user_avatar_default = user_avatar_default;
    }

    public long getToken_expires_time() {
        return token_expires_time;
    }

    public void setToken_expires_time(long token_expires_time) {
        this.token_expires_time = token_expires_time;
    }

    public String getHeader_key_token() {
        return header_key_token;
    }

    public void setHeader_key_token(String header_key_token) {
        this.header_key_token = header_key_token;
    }

    public String getHeader_key_current_userid() {
        return header_key_current_userid;
    }

    public void setHeader_key_current_userid(String header_key_current_userid) {
        this.header_key_current_userid = header_key_current_userid;
    }
}
