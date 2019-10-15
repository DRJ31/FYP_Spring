package com.suzumiya.dao;

import com.suzumiya.model.Favorite;
import com.suzumiya.model.Syllabus;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class SyllabusDao extends SqlSessionDaoSupport implements Dao<Syllabus> {
    @Override
    public List<Syllabus> selectAll() {
        return getSqlSession().selectList("com.suzumiya.mapper.SyllabusMapper.selectAll");
    }

    @Override
    public Syllabus selectById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.SyllabusMapper.selectBySyllabusId", id);
    }

    public List<Syllabus> selectFavoriteSyllabuses(@Param("uid") int uid) {
        return getSqlSession().selectList("com.suzumiya.mapper.SyllabusMapper.selectFavoriteSyllabuses", uid);
    }

    public void insertSyllabus(Syllabus syllabus){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertSyllabus", syllabus);
    }

    public void deleteSyllabus(@Param("sid") int sid){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteSyllabus", sid);
    }

    public void insertFavoriteSyllabus(Favorite favorite){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertFavoriteSyllabus", favorite);
    }

    public void deleteFavoriteSyllabus(Favorite favorite){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteFavoriteSyllabus", favorite);
    }
}