package com.shtrade.tradeservice.service;

import com.shtrade.tradeservice.entity.ItemPublish;

public interface ItemService {

    public ItemPublish createItem(ItemPublish itemPublish, int sellerId);
}
