package com.suzumiya.dao;

import com.suzumiya.model.School;
import com.suzumiya.model.Syllabus;
import com.suzumiya.model.User;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class SchoolDao extends SqlSessionDaoSupport implements Dao<School> {
    @Override
    public List<School> selectAll() {
        return getSqlSession().selectList("com.suzumiya.mapper.SchoolMapper.selectAll");
    }

    @Override
    public School selectById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.SchoolMapper.selectBySchoolId", id);
    }

    public List<School> selectFavoriteSchools(@Param("uid") int uid) {
        return getSqlSession().selectList("com.suzumiya.mapper.SchoolMapper.selectFavoriteSchools", uid);
    }
}