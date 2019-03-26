package com.fantj.sbmybatis.mapper;

import com.fantj.sbmybatis.model.UserAuth;

public interface UserAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    UserAuth selectByPrimaryKey(Integer id);

    UserAuth selectByTypeAndIdentifier(String identityType, String identifier);

    int updateByPrimaryKeySelective(UserAuth record);

    int updateByPrimaryKey(UserAuth record);
}