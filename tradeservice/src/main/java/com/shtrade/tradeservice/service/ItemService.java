package com.shtrade.tradeservice.service;

import com.fantj.sbmybatis.model.Item;
import com.github.pagehelper.PageInfo;
import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemPost;
import com.shtrade.tradeservice.entity.ItemPublish;

public interface ItemService {

    public ItemPublish createItem(ItemPublish itemPublish, int sellerId);

    public PageInfo<ItemBrief> selectItemBriefList(Integer page, Integer size);

    public PageInfo<ItemPost> selectItemPostList(Integer page, Integer size, Integer seller);

    public Item selectItemDetail(int id);
}
