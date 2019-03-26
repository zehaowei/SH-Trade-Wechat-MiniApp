package com.shtrade.userservice.service;

import com.shtrade.userservice.entity.AuthToken;

public interface TokenService {
    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @return 生成的token
     */
    public AuthToken createToken(int userId);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    public boolean checkToken(AuthToken model);

    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     * @return
     */
    public AuthToken getToken(String authentication);

    /**
     * 清除token
     * @param userId 登录用户的id
     */
    public void deleteToken(int userId);
}
