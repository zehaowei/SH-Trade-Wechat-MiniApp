package com.shtrade.userservice.controller;

import com.fantj.sbmybatis.model.UserAuth;
import com.shtrade.userservice.conf.Constant;
import com.shtrade.userservice.entity.AuthToken;
import com.shtrade.userservice.service.TokenServiceRedis;
import com.shtrade.userservice.service.UserServiceImpl;
import com.shtrade.userservice.util.DataIllegalException;
import com.shtrade.userservice.util.UserAuthorization;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TokenController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TokenServiceRedis tokenServiceRedis;

    @PostMapping("/userservice/api/token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken login(@RequestBody UserAuth userAuth) throws DataIllegalException {
        return userService.login(userAuth);
    }

    @DeleteMapping("/userservice/api/token")
    @ApiImplicitParams({
            @ApiImplicitParam(name="cookie",value="用户身份验证token",required=true,paramType="header"),
            @ApiImplicitParam(name="userId",value="无需提供，在cookie字段中提供token即可",paramType="path")})
    @UserAuthorization
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity logout(@RequestHeader(value="currentUserId") int userId) {
        tokenServiceRedis.deleteToken(userId);
        return null;
    }

//    @GetMapping("/userservice/api/v1/token")
//    @ResponseStatus(HttpStatus.OK)
//    public AuthToken getUserToken(@RequestBody AuthToken authToken) {
//
//    }

}
