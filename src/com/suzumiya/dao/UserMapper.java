package com.suzumiya.dao;

import com.suzumiya.model.User;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapper extends SqlSessionDaoSupport implements Dao<User> {
    @Override
    public List<User> selectAll() {
        return getSqlSession().selectList("com.suzumiya.mapper.UserMapper.selectAll");
    }
}
