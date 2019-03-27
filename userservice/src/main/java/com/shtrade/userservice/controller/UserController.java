package com.shtrade.userservice.controller;

import com.fantj.sbmybatis.model.UserAuth;
import com.shtrade.userservice.service.UserServiceImpl;
import com.shtrade.userservice.util.DataIllegalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/userservice/api/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAuth register(@RequestBody UserAuth userAuth) throws DataIllegalException {
        return userService.register(userAuth);
    }

}
