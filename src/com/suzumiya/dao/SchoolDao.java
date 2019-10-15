package com.suzumiya.dao;

import com.suzumiya.model.Favorite;
import com.suzumiya.model.School;
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

    public void insertSchool(School school){
        getSqlSession().insert("com.suzumiya.mapper.SchoolMapper.insertSchool", school);
    }

    public void deleteSchool(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SchoolMapper.deleteSchool", id);
    }

    public void insertFavoriteSchool(Favorite favorite){
        getSqlSession().insert("com.suzumiya.mapper.SchoolMapper.insertFavoriteSchool", favorite);
    }

    public void deleteFavoriteSchool(Favorite favorite){
        getSqlSession().delete("com.suzumiya.mapper.SchoolMapper.deleteFavoriteSchool", favorite);
    }

    public Favorite checkFavoriteDuplicate(Favorite favorite){
        return getSqlSession().selectOne("com.suzumiya.mapper.SchoolMapper.checkFavoriteDuplicate", favorite);
    }
}