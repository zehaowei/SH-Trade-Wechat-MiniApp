package com.shtrade.tradeservice.controller;

import com.fantj.sbmybatis.model.Item;
import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemDetail;
import com.shtrade.tradeservice.entity.ItemPost;
import com.shtrade.tradeservice.entity.ItemPublish;
import com.shtrade.tradeservice.service.ItemServiceImpl;
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

    @PostMapping("/tradeservice/api/item")
    @UserAuthorization
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("发布商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="path")
    })
    public ItemPublish publishItem(@RequestBody ItemPublish itemPublish,
                                   @RequestHeader(value="currentUserId") int userId) {
        return itemService.createItem(itemPublish, userId);
    }

    @GetMapping(value="/tradeservice/api/item",params = { "type=brief" })
    @UserAuthorization
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取首页商品信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="商品信息类型:brief",required=true,paramType="query"),
            @ApiImplicitParam(name="page",value="商品列表页码数，每页10条信息",required=true,paramType="query")
    })
    public List<ItemBrief> getBriefItemsList(@RequestParam("page") int page) {
        return itemService.selectItemBriefList(page, 10).getList();
    }

    @GetMapping(value="/tradeservice/api/item",params = { "type=search" })
    @UserAuthorization
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取商品搜索结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="商品信息类型:search",required=true,paramType="query"),
            @ApiImplicitParam(name="searchStr",value="用户搜索输入",required=true,paramType="query")
    })
    public List<ItemBrief> getSearchItemsList(@RequestParam("searchStr") String str) {

    }

    @GetMapping(value="/tradeservice/api/item",params = { "type=mypost" })
    @UserAuthorization
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取用户发布的商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="type",value="商品信息类型:mypost",required=true,paramType="query"),
            @ApiImplicitParam(name="page",value="商品列表页码数，每页10条信息",required=true,paramType="query"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="path")
    })
    public List<ItemPost> getItemPostList(@RequestParam("page") int page,
                                          @RequestHeader(value="currentUserId") int userId) {
        return itemService.selectItemPostList(page, 10, userId).getList();
    }

    @GetMapping(value="/tradeservice/api/item",params = { "type=detail" })
    @UserAuthorization
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取一件商品的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="type",value="商品信息类型:detail",required=true,paramType="query"),
            @ApiImplicitParam(name="itemId",value="商品ID",required=true,paramType="query")
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
        // todo 调用微服务获取user信息，设置seller
        return itemDetail;
    }
}
