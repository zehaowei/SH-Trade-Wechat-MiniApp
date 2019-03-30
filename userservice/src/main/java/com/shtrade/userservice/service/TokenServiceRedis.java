package com.shtrade.userservice.service;

import com.shtrade.userservice.conf.Constant;
import com.shtrade.userservice.entity.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceRedis implements TokenService {

    private RedisTemplate<String, String> redis;
    private Constant constant;

    @Autowired
    public void setRedis(RedisTemplate<String, String> redis) {
        this.redis = redis;
    }

    @Autowired
    public void setConstant(Constant constant) {
        this.constant = constant;
    }

    public AuthToken createToken(int userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        AuthToken authToken = new AuthToken(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(((Integer) userId).toString()).set(token, constant.getToken_expires_time(), TimeUnit.DAYS);
        return authToken;
    }

    public AuthToken getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param = authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        //使用userId和源token简单拼接成的token，可以增加加密措施
        int userId = Integer.parseInt(param[0]);
        String token = param[1];
        return new AuthToken(userId, token);
    }

    public boolean checkToken(AuthToken authToken) {
        if (authToken == null) {
            return false;
        }
        String token = redis.boundValueOps(((Integer) authToken.getUserId()).toString()).get();
        if (token == null || !token.equals(authToken.getToken())) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        redis.boundValueOps(((Integer) authToken.getUserId()).toString()).expire(constant.getToken_expires_time(), TimeUnit.DAYS);
        return true;
    }

    public void deleteToken(int userId) {
        redis.delete(((Integer) userId).toString());
    }
}
