package com.shtrade.tradeservice.controller;

import com.shtrade.tradeservice.entity.ItemBrief;
import com.shtrade.tradeservice.entity.ItemDetail;
import com.shtrade.tradeservice.entity.ItemPublish;
import com.shtrade.tradeservice.service.ItemServiceImpl;
import com.shtrade.tradeservice.util.UserAuthorization;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @PostMapping("/tradeservice/api/item")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="path")})
    @UserAuthorization
    public ItemPublish publishItem(@RequestBody ItemPublish itemPublish,
                                   @RequestHeader(value="currentUserId") int userId) {
        return itemService.createItem(itemPublish, userId);
    }

    @GetMapping(value="/tradeservice/api/item",params = { "type=brief" })
    @ResponseStatus(HttpStatus.OK)
    public List<ItemBrief> getBriefItemsList(@RequestParam("page") int page) {

    }

    @GetMapping(value="/tradeservice/api/item",params = { "type=search" })
    @ApiImplicitParam(name="searchStr",value="用户搜索输入",required=true,paramType="query")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemBrief> getSearchItemsList(@RequestParam("searchStr") String str) {

    }

    @GetMapping(value="/tradeservice/api/item",params = { "type=detail" })
    @ApiImplicitParam(name="itemId",value="商品ID",required=true,paramType="query")
    @ResponseStatus(HttpStatus.OK)
    public ItemDetail getDetailItem(@RequestParam("itemId") String id) {

    }
}
