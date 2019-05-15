package com.shtrade.tradeservice.controller;

import com.fantj.sbmybatis.model.Item;
import com.shtrade.tradeservice.conf.Constant;
import com.shtrade.tradeservice.entity.*;
import com.shtrade.tradeservice.service.ItemServiceImpl;
import com.shtrade.tradeservice.service.UserServiceRemote;
import com.shtrade.tradeservice.util.UserAuthorization;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @Autowired
    UserServiceRemote userServiceRemote;

    @Autowired
    Constant constant;

    @PostMapping("/tradeservice/api/items")
    @UserAuthorization
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("发布商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="path")
    })
    public ItemPublish publishItem(@RequestBody ItemPublish itemPublish,
                                   @RequestAttribute(value="currentUserId") int userId) {
        return itemService.createItem(itemPublish, userId);
    }

    @GetMapping(value="/tradeservice/api/items/brief")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取首页商品信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="商品列表页码数，每页10条信息",required=true,paramType="query",dataType="integer")
    })
    public List<ItemBrief> getBriefItemsList(@RequestParam("page") int page) {
        return itemService.selectItemBriefList(page, 10).getList();
    }

    @GetMapping(value="/tradeservice/api/items/seeking")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取商品搜索结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="搜索结果页码数，每页10条信息",required=true,paramType="query",dataType="integer"),
            @ApiImplicitParam(name="searchStr",value="用户搜索输入",required=true,paramType="query")
    })
    public List<ItemElasticIndex> getSearchItemsList(@RequestParam("page") int page,
                                                     @RequestParam("searchStr") String str) {
        return itemService.searchItems(page, 10, str);
    }

    @GetMapping(value="/tradeservice/api/items/mypost")
    @UserAuthorization
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取用户发布的商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="page",value="商品列表页码数，每页10条信息",required=true,paramType="query",dataType="integer"),
            @ApiImplicitParam(name="status",value="商品状态过滤条件:all(全部), onsale(销售中), offshelf(已下架)",paramType="query"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="header")
    })
    public List<ItemPost> getItemPostList(@RequestParam("page") int page,
                                          @RequestParam("status") String status,
                                          @RequestAttribute(value="currentUserId") int userId) {
        return itemService.selectItemPostListByStatus(page, 10, userId, status).getList();
    }

    @GetMapping(value="/tradeservice/api/items")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取一件商品的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="itemId",value="商品id",paramType="query")
    })
    public ItemDetail getDetailItem(@RequestParam("itemId") int id) {
        Item item = itemService.selectItemDetail(id);
        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setId(item.getId());
        itemDetail.setImgUrl(item.getImgUrl());
        itemDetail.setName(item.getName());
        itemDetail.setPrice(item.getPrice());
        itemDetail.setAddress(item.getAddress());
        itemDetail.setNotes(item.getNotes());
        UserDetail seller = new UserDetail(userServiceRemote.getUser(item.getSeller()));
        String identifier = userServiceRemote.getIdentifier(item.getSeller());
        seller.setIdentifier(identifier);
        itemDetail.setSeller(seller);
        return itemDetail;
    }

    @DeleteMapping(value="/tradeservice/api/items")
    @UserAuthorization
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("下架商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header")
    })
    public void deleteItem(@RequestParam("itemId") int id) {
        itemService.updateItemStatus(id, constant.getItem_status_offshelf());
    }
}
