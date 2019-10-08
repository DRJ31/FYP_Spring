package com.suzumiya.dao;

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
}