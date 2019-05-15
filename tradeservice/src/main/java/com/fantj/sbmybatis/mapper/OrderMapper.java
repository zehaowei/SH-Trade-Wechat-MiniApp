package com.fantj.sbmybatis.mapper;

import com.fantj.sbmybatis.model.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectBySellerOrBuyer(Integer userId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}