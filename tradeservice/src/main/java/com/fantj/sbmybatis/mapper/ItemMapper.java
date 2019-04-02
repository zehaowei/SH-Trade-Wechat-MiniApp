package com.fantj.sbmybatis.mapper;

import com.fantj.sbmybatis.model.Item;
import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemPost;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    List<ItemBrief> selectItemBriefList();

    List<ItemPost> selectItemPostListByUserId(int seller);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);
}