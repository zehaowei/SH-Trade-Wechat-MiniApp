package com.shtrade.userservice.controller;

import com.fantj.sbmybatis.model.User;
import com.fantj.sbmybatis.model.UserAuth;
import com.shtrade.userservice.service.UserServiceImpl;
import com.shtrade.userservice.util.DataIllegalException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/userservice/api/user")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("用户注册")
    public UserAuth register(@RequestBody UserAuth userAuth) throws DataIllegalException {
        return userService.register(userAuth);
    }

    @GetMapping("/userservice/api/user")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取用户信息")
    public User getUser(@RequestParam("userId") int userId) {
        return userService.selectById(userId);
    }

}
