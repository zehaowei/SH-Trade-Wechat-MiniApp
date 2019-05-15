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
    private String item_status_all;
    private String item_status_ontrade;

    private String order_status_ontrade;
    private String order_status_cancel;
    private String order_status_finish;


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

    public String getItem_status_all() {
        return item_status_all;
    }

    public void setItem_status_all(String item_status_all) {
        this.item_status_all = item_status_all;
    }

    public String getItem_status_ontrade() {
        return item_status_ontrade;
    }

    public void setItem_status_ontrade(String item_status_ontrade) {
        this.item_status_ontrade = item_status_ontrade;
    }

    public String getOrder_status_ontrade() {
        return order_status_ontrade;
    }

    public void setOrder_status_ontrade(String order_status_ontrade) {
        this.order_status_ontrade = order_status_ontrade;
    }

    public String getOrder_status_cancel() {
        return order_status_cancel;
    }

    public void setOrder_status_cancel(String order_status_cancel) {
        this.order_status_cancel = order_status_cancel;
    }

    public String getOrder_status_finish() {
        return order_status_finish;
    }

    public void setOrder_status_finish(String order_status_finish) {
        this.order_status_finish = order_status_finish;
    }

}
