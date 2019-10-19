package com.suzumiya.dao;

import com.suzumiya.model.*;
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

    public Syllabus selectByInfo(Syllabus syllabus) {
        return getSqlSession().selectOne("com.suzumiya.mapper.SyllabusMapper.selectByInfo", syllabus);
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

    public Favorite checkFavoriteDuplicate(Favorite favorite){
        return getSqlSession().selectOne("com.suzumiya.mapper.SyllabusMapper.checkFavoriteDuplicate", favorite);
    }

    public List<Cilo> selectAllCilo(){
        return getSqlSession().selectList("com.suzumiya.mapper.SyllabusMapper.selectAllCilo");
    }

    public Cilo selectCiloById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.SyllabusMapper.selectCiloById", id);
    }

    public void insertCilo(Cilo cilo){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertCilo", cilo);
    }

    public void deleteCilo(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteCilo", id);
    }

    public void insertPilo(Pilo pilo){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertPilo", pilo);
    }

    public void deletePilo(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deletePilo", id);
    }

    public void insertPC(Pilo_Cilo pilo_cilo){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertPC", pilo_cilo);
    }

    public void deletePC(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deletePC", id);
    }

    public void insertTla(Tla tla){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertTla", tla);
    }

    public void deleteTla(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteTla", id);
    }

    public void insertContent(Content content){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertContent", content);
    }

    public void deleteContent(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteContent", id);
    }
}