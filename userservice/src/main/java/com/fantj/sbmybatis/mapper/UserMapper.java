package com.fantj.sbmybatis.mapper;

import com.fantj.sbmybatis.model.User;
import org.springframework.stereotype.Repository;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}