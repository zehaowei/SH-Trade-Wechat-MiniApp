package com.shtrade.tradeservice.service;

import com.fantj.sbmybatis.mapper.ItemMapper;
import com.fantj.sbmybatis.mapper.OrderMapper;
import com.fantj.sbmybatis.model.Order;
import com.shtrade.tradeservice.conf.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    Constant constant;

    public int createOrder(int buyer, int itemId) {
        int seller = itemMapper.selectSellerByPK(itemId);
        Order order = new Order();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        order.setDate(formatter.format(currentTime));
        order.setBuyer(buyer);
        order.setSeller(seller);
        order.setItemId(itemId);
        order.setStatus(constant.getOrder_status_ontrade());
        return orderMapper.insert(order);
    }

    public List<Order> getOrderByStatus(int userId, String status) {
        List<Order> orderList = orderMapper.selectBySellerOrBuyer(userId);
        List<Order> result;
        if (status.equals(constant.getItem_status_all())){
            result = orderList;
        } else {
            result = new ArrayList<>();
            for (Order order : orderList) {
                if (order.getStatus().equals(status))
                    result.add(order);
            }
        }
        return result;
    }

    public int updateOrderStatus(int orderId, String status) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    public Order getOrderByPK(int orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }
}
