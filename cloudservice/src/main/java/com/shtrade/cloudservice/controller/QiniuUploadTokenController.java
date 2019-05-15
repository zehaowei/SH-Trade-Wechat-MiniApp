package com.shtrade.cloudservice.controller;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QiniuUploadTokenController {

    @GetMapping("/cloudservice/api/qiniutoken")
    @ResponseStatus(HttpStatus.OK)
    public String getUploadToken() {
        String accessKey = "9rPV_fCrOJ624vKXVFe37jIoYCoy2t3BbiQLIxnX";
        String secretKey = "EkW7DuA7iDJnA1mnVzRKVcu4-UF8Tg-zDpJCEyYD";
        String bucket = "sh-trade-img";
        long expireSeconds = 6000;   //过期时间
        StringMap putPolicy = new StringMap();
        StringMap returnBody = new StringMap();
        putPolicy.put("scope", "sh-trade-img");
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket,null, expireSeconds, putPolicy);
        return upToken;
    }
}
