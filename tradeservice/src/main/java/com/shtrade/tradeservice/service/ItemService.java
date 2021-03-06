package com.shtrade.tradeservice.service;

import com.fantj.sbmybatis.model.Item;
import com.github.pagehelper.PageInfo;
import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemElasticIndex;
import com.shtrade.tradeservice.entity.ItemPost;
import com.shtrade.tradeservice.entity.ItemPublish;

import java.util.List;

public interface ItemService {

    public ItemPublish createItem(ItemPublish itemPublish, int sellerId);

    public PageInfo<ItemBrief> selectItemBriefList(Integer page, Integer size);

    public ItemPublish selectItemPublishById(Integer itemId);

    public PageInfo<ItemPost> selectItemPostListByStatus(Integer page, Integer size, Integer seller, String status);

    public List<ItemElasticIndex> searchItems(Integer page, Integer size, String str);

    public Item selectItemDetail(int id);

    public int getSellerByPK(int itemId);

    public int updateItemStatus(int itemId, String status);
}
