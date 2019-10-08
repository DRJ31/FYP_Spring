package com.suzumiya.dao;

import com.suzumiya.model.User;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserDao extends SqlSessionDaoSupport implements Dao<User> {
    @Override
    public List<User> selectAll() {
        return getSqlSession().selectList("com.suzumiya.mapper.UserMapper.selectAll");
    }

    @Override
    public User selectById(@Param("uid") int uid){
        return getSqlSession().selectOne("com.suzumiya.mapper.UserMapper.selectByUid", uid);
    }

}
