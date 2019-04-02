package com.shtrade.tradeservice.service;

import com.fantj.sbmybatis.mapper.ItemMapper;
import com.fantj.sbmybatis.model.Item;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shtrade.tradeservice.conf.Constant;
import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemPost;
import com.shtrade.tradeservice.entity.ItemPublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    Constant constant;

    @Override
    public ItemPublish createItem(ItemPublish itemPublish, int sellerId) {
        Item item = new Item();
        item.setName(itemPublish.getName());
        item.setImgUrl(itemPublish.getImgUrl());
        item.setPrice(itemPublish.getPrice());
        if (itemPublish.getAddress() != null) {
            item.setAddress(itemPublish.getAddress());
        } else {
            item.setAddress("");
        }
        if (itemPublish.getNotes() != null) {
            item.setNotes(itemPublish.getNotes());
        } else {
            item.setNotes("");
        }
        item.setSeller(sellerId);
        item.setStatus(constant.getItem_status_onsale());
        int num = itemMapper.insert(item);
        if (num == 1) {
            return itemPublish;
        } else {
            return null;
        }
    }

    @Override
    public PageInfo<ItemBrief> selectItemBriefList(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ItemBrief> itemBriefList = itemMapper.selectItemBriefList();
        PageInfo<ItemBrief> pageInfo = new PageInfo<>(itemBriefList);
        return pageInfo;
    }

    @Override
    public PageInfo<ItemPost> selectItemPostList(Integer page, Integer size, Integer seller) {
        PageHelper.startPage(page, size);
        List<ItemPost> itemPostList = itemMapper.selectItemPostListByUserId(seller);
        PageInfo<ItemPost> pageInfo = new PageInfo<>(itemPostList);
        return pageInfo;
    }

    @Override
    public Item selectItemDetail(int id) {
        return itemMapper.selectByPrimaryKey(id);
    }
}
