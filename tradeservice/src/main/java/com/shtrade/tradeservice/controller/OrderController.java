package com.shtrade.tradeservice.controller;

import com.fantj.sbmybatis.model.Order;
import com.shtrade.tradeservice.conf.Constant;
import com.shtrade.tradeservice.entity.OrderDetail;
import com.shtrade.tradeservice.entity.UserDetail;
import com.shtrade.tradeservice.service.ItemServiceImpl;
import com.shtrade.tradeservice.service.OrderServiceImpl;
import com.shtrade.tradeservice.service.UserServiceRemote;
import com.shtrade.tradeservice.util.UserAuthorization;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    ItemServiceImpl itemService;

    @Autowired
    UserServiceRemote userServiceRemote;

    @Autowired
    Constant constant;

    @PostMapping("/tradeservice/api/orders")
    @UserAuthorization
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="header"),
            @ApiImplicitParam(name="itemId",value="商品id",required=true,paramType="query")
    })
    public int createOrder(@RequestParam("itemId") int itemId,
                           @RequestAttribute(value="currentUserId") int userId) {
        itemService.updateItemStatus(itemId, constant.getItem_status_ontrade());
        return orderService.createOrder(userId, itemId);
    }

    @GetMapping("/tradeservice/api/orders")
    @UserAuthorization
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获得订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="path"),
            @ApiImplicitParam(name="status",value="商品状态过滤条件:all(全部), ontrade(交易中), finish(已完成), cancel(已取消)")
    })
    public List<OrderDetail> getOrderList(@RequestParam("status") String status,
                                          @RequestAttribute(value="currentUserId") int userId) {
        List<Order> orderList = orderService.getOrderByStatus(userId, status);
        List<OrderDetail> result = new ArrayList<>();
        for (Order order : orderList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(order.getId());
            orderDetail.setDate(order.getDate());

            if (order.getStatus().equals(constant.getOrder_status_ontrade()))
                orderDetail.setStatus("交易中");
            else if (order.getStatus().equals(constant.getOrder_status_finish()))
                orderDetail.setStatus("已完成");
            else
                orderDetail.setStatus("已取消");

            UserDetail seller = new UserDetail(userServiceRemote.getUser(order.getSeller()));
            seller.setIdentifier(userServiceRemote.getIdentifier(order.getSeller()));
            orderDetail.setSeller(seller);

            UserDetail buyer = new UserDetail(userServiceRemote.getUser(order.getBuyer()));
            buyer.setIdentifier(userServiceRemote.getIdentifier(order.getBuyer()));
            orderDetail.setBuyer(buyer);

            orderDetail.setItemPublish(itemService.selectItemPublishById(order.getItemId()));

            result.add(orderDetail);
        }
        return result;
    }

    @PutMapping("/tradeservice/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("修改订单状态")
    public void updateOrderStatus(@RequestParam("orderId") int orderId,
                                  @RequestParam("status") String status) {
        orderService.updateOrderStatus(orderId, status);
        int itemId = orderService.getOrderByPK(orderId).getItemId();
        if (status.equals(constant.getOrder_status_finish())) {
            itemService.updateItemStatus(itemId, constant.getItem_status_offshelf());
        } else if (status.equals(constant.getOrder_status_cancel())) {
            itemService.updateItemStatus(itemId, constant.getItem_status_onsale());
        }

    }
}
