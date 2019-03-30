package com.shtrade.tradeservice.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "constant")
@PropertySource("constant.properties")
public class Constant {

    private String header_key_token;
    private String header_key_current_userid;

    private String item_status_onsale;
    private String item_status_offshelf;


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

    public String getItem_status_onsale() {
        return item_status_onsale;
    }

    public void setItem_status_onsale(String item_status_onsale) {
        this.item_status_onsale = item_status_onsale;
    }

    public String getItem_status_offshelf() {
        return item_status_offshelf;
    }

    public void setItem_status_offshelf(String item_status_offshelf) {
        this.item_status_offshelf = item_status_offshelf;
    }

}
