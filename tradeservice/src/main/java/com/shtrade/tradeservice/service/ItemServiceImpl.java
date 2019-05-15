package com.shtrade.tradeservice.service;

import com.fantj.sbmybatis.mapper.ItemMapper;
import com.fantj.sbmybatis.model.Item;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shtrade.tradeservice.conf.Constant;
import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemElasticIndex;
import com.shtrade.tradeservice.entity.ItemPost;
import com.shtrade.tradeservice.entity.ItemPublish;
import com.shtrade.tradeservice.util.ItemRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    Constant constant;

    @Override
    public ItemPublish createItem(ItemPublish itemPublish, int sellerId) {
        Item item = new Item();
        item.setName(itemPublish.getName());
        item.setImgUrl(itemPublish.getImgUrl());
        item.setPrice(itemPublish.getPrice());
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        item.setDate(formatter.format(currentTime));
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
            ItemElasticIndex itemElasticIndex = new ItemElasticIndex();
            itemElasticIndex.setId(item.getId());
            itemElasticIndex.setName(item.getName());
            itemElasticIndex.setImgUrl(item.getImgUrl());
            itemElasticIndex.setNotes(item.getNotes());
            itemElasticIndex.setPrice(item.getPrice());
            itemRepository.save(itemElasticIndex);
            itemPublish.setId(item.getId());
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
    public ItemPublish selectItemPublishById(Integer itemId) {
        return itemMapper.selectItemPublishById(itemId);
    }

    @Override
    public PageInfo<ItemPost> selectItemPostListByStatus(Integer page, Integer size,
                                                         Integer seller, String status) {
        PageHelper.startPage(page, size);
        List<ItemPost> itemPostList;
        if (status.equals(constant.getItem_status_all())) {
            itemPostList = itemMapper.selectItemPostListByUserId(seller);
        } else {
            itemPostList = itemMapper.selectItemPostListByUserIdAndStatus(seller, status);
        }
        for (ItemPost itemPost : itemPostList) {
            if (itemPost.getStatus().equals(constant.getItem_status_onsale()))
                itemPost.setStatus("销售中");
            else if (itemPost.getStatus().equals(constant.getItem_status_offshelf()))
                itemPost.setStatus("已下架");
            else
                itemPost.setStatus("交易中");
        }
        PageInfo<ItemPost> pageInfo = new PageInfo<>(itemPostList);
        return pageInfo;
    }

    @Override
    public Item selectItemDetail(int id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemElasticIndex> searchItems(Integer page, Integer size, String str) {
//        // 构建查询条件
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        // 添加基本分词查询
//        queryBuilder.withQuery(QueryBuilders.termQuery("name", str));
//        // 分页：
//        queryBuilder.withPageable(PageRequest.of(page,size));
//        // 搜索，获取结果
//        Page<ItemElasticIndex> items = this.itemRepository.search(queryBuilder.build());
//
//        return items.getContent();
        List<ItemElasticIndex> list = itemRepository.findByNameLike(str);
        List<ItemElasticIndex> newList = new ArrayList<>();
        for (ItemElasticIndex item : list) {
            if (itemMapper.selectStatusByPK(item.getId()).equals(constant.getItem_status_onsale())) {
                newList.add(item);
            }
        }
        return newList;
    }

    @Override
    public int updateItemStatus(int itemId, String status) {
        Item item = new Item();
        item.setId(itemId);
        item.setStatus(status);
        return itemMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public int getSellerByPK(int itemId) {
        return itemMapper.selectSellerByPK(itemId);
    }
}
