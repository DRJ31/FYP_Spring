package com.suzumiya.dao;

import com.suzumiya.model.School;
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

    public void insertUser(User user){
        getSqlSession().insert("com.suzumiya.mapper.UserMapper.insertUser", user);
    }

    public void deleteUser(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.UserMapper.deleteUser", id);
    }
    public User userLogin(User user){
        return getSqlSession().selectOne("com.suzumiya.mapper.UserMapper.userLogin", user);
    }

    public User checkUserDuplicate(User user){
        return getSqlSession().selectOne("com.suzumiya.mapper.UserMapper.checkUserDuplicate", user);
    }

    public void updateSchoolRole(@Param("id") int id){
        getSqlSession().update("com.suzumiya.mapper.UserMapper.updateSchoolRole", id);
    }

    public void updateSchoolId(User user){
        getSqlSession().update("com.suzumiya.mapper.UserMapper.updateSchoolId", user);
    }

}
