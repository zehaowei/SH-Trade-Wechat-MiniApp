package com.shtrade.userservice.service;

import com.fantj.sbmybatis.mapper.UserAuthMapper;
import com.fantj.sbmybatis.mapper.UserMapper;
import com.fantj.sbmybatis.model.User;
import com.fantj.sbmybatis.model.UserAuth;
import com.shtrade.userservice.conf.Constant;
import com.shtrade.userservice.entity.AuthToken;
import com.shtrade.userservice.entity.ErrorResult;
import com.shtrade.userservice.util.DataIllegalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthMapper userAuthMapper;
    @Autowired
    private Constant constant;
    @Autowired
    private TokenServiceRedis tokenServiceRedis;

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public UserAuth register(UserAuth userAuth) throws DataIllegalException {
        UserAuth result = userAuthMapper.selectByTypeAndIdentifier(
                userAuth.getIdentityType(), userAuth.getIdentifier());
        String identityType = userAuth.getIdentityType();
        if (identityType.equals(constant.getIdentity_type_username())) {
            if (result == null) {
                User user = new User();
                user.setNickname(userAuth.getIdentifier());
                user.setAvatar(constant.getUser_avatar_default());
                int insertNum = userMapper.insert(user);
                if (insertNum != 0) {
                    userAuth.setUserId(user.getId());
                    if (userAuthMapper.insert(userAuth) == 1) {
                        return userAuth;
                    } else {
                        // 内部错误打印日志
                    }
                } else {
                    // 内部错误打印日志
                }
            } else {
                throw new DataIllegalException("username has existed", ErrorResult.ErrorCode.USER_ALREADY_EXIST.getCode());
            }
        }
//        else if (identityType.equals(constant.IDENTITY_TYPE_WECHAT)) {
//            // todo
//        }
        return null;
    }

    @Override
    public User selectById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public AuthToken login(UserAuth userAuth) throws DataIllegalException {
        UserAuth result = userAuthMapper.selectByTypeAndIdentifier(userAuth.getIdentityType(),
                userAuth.getIdentifier());
        if (result == null) {
            throw new DataIllegalException("user does not exist", ErrorResult.ErrorCode.USER_NOT_FOUND.getCode());
        } else {
            if (result.getCredential().equals(userAuth.getCredential())) {
                return tokenServiceRedis.createToken(result.getUserId());
            } else {
                throw new DataIllegalException("Password is incorrect", ErrorResult.ErrorCode.USER_PASSWD_ERROR.getCode());
            }
        }
    }
}
