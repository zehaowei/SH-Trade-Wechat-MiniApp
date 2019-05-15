package com.fantj.sbmybatis.mapper;

import com.fantj.sbmybatis.model.Item;
import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemPost;
import com.shtrade.tradeservice.entity.ItemPublish;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    List<ItemBrief> selectItemBriefList();

    ItemPublish selectItemPublishById(Integer id);

    List<ItemPost> selectItemPostListByUserId(int seller);

    List<ItemPost> selectItemPostListByUserIdAndStatus(int seller, String status);

    String selectStatusByPK(int itemId);

    Integer selectSellerByPK(int itemId);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}