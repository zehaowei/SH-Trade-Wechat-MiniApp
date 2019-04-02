package com.shtrade.userservice.service;

import com.fantj.sbmybatis.model.User;
import com.fantj.sbmybatis.model.UserAuth;
import com.shtrade.userservice.entity.AuthToken;
import com.shtrade.userservice.util.DataIllegalException;

public interface UserService {
    //public void delete(int id);

    public UserAuth register(UserAuth userAuth) throws DataIllegalException;

    public AuthToken login(UserAuth userAuth) throws DataIllegalException;

    public int update(User user);

    public User selectById(int id);

}
