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

    @PostMapping("/userservice/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("用户注册")
    public UserAuth register(@RequestBody UserAuth userAuth) throws DataIllegalException {
        return userService.register(userAuth);
    }

    @PutMapping("/userservice/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("更新用户昵称和头像")
    public User putUserInfo(@RequestBody User user) throws DataIllegalException {
        if (userService.update(user) == 1) {
            return user;
        } else {
            return null;
        }
    }

    @GetMapping("/userservice/api/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取用户信息")
    public User getUser(@PathVariable("userId") int userId) {
        return userService.selectById(userId);
    }

    @GetMapping("/userservice/api/identifier/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("获取用户名")
    public String getIdentifier(@PathVariable("userId") int userId) {
        return userService.getIdentifierByUserId(userId);
    }

}
