package com.shtrade.userservice.controller;

import com.fantj.sbmybatis.model.User;
import com.fantj.sbmybatis.model.UserAuth;
import com.shtrade.userservice.entity.AuthToken;
import com.shtrade.userservice.service.TokenServiceRedis;
import com.shtrade.userservice.service.UserServiceImpl;
import com.shtrade.userservice.util.CurrentUser;
import com.shtrade.userservice.util.DataIllegalException;
import com.shtrade.userservice.util.UserAuthorization;
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
    @UserAuthorization
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity logout(@CurrentUser User user) {
        tokenServiceRedis.deleteToken(user.getId());
        return null;
    }

//    @GetMapping("/userservice/api/v1/token")
//    @ResponseStatus(HttpStatus.OK)
//    public AuthToken getUserToken(@RequestBody AuthToken authToken) {
//
//    }

}
