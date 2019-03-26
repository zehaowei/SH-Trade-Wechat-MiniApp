package com.shtrade.userservice.controller;

import com.fantj.sbmybatis.model.UserAuth;
import com.shtrade.userservice.entity.AuthToken;
import com.shtrade.userservice.service.UserServiceImpl;
import com.shtrade.userservice.util.DataIllegalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TokenController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/userservice/api/v1/token")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthToken login(@RequestBody UserAuth userAuth) throws DataIllegalException {
        return userService.login(userAuth);
    }

//    @GetMapping("/userservice/api/v1/token")
//    @ResponseStatus(HttpStatus.OK)
//    public AuthToken getUserToken(@RequestBody AuthToken authToken) {
//
//    }
}
