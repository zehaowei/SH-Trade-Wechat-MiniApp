package com.shtrade.tradeservice.service;

import com.shtrade.tradeservice.entity.AuthToken;
import com.shtrade.tradeservice.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service")
public interface UserServiceRemote {

    @GetMapping("/userservice/api/users/{userId}")
    public User getUser(@PathVariable("userId") int userId);

    @GetMapping("/userservice/api/token")
    public AuthToken getUserToken(@RequestParam("auth") String authorization);

    @GetMapping("/userservice/api/identifier/{userId}")
    public String getIdentifier(@PathVariable("userId") int userId);
}
