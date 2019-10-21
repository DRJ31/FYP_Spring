package com.suzumiya.dao;

import com.suzumiya.model.*;
import com.suzumiya.model.relationship.PiloCilo;
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

    public int insertCilo(Cilo cilo){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertCilo", cilo);
        return cilo.getId();
    }

    public void deleteCilo(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteCilo", id);
    }

    public int insertPilo(Pilo pilo){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertPilo", pilo);
        return pilo.getId();
    }

    public void deletePilo(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deletePilo", id);
    }

    public int insertPC(PiloCilo pilo_cilo){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertPC", pilo_cilo);
        return pilo_cilo.getId();
    }

    public void deletePC(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deletePC", id);
    }

    public int insertTla(Tla tla){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertTla", tla);
        return tla.getId();
    }

    public void deleteTla(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteTla", id);
    }

    public int insertContent(Content content){
        getSqlSession().insert("com.suzumiya.mapper.SyllabusMapper.insertContent", content);
        return content.getId();
    }

    public void deleteContent(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SyllabusMapper.deleteContent", id);
    }

    public List<Syllabus> selectAll_S(@Param("id") int id){
        return getSqlSession().selectList("com.suzumiya.mapper.SyllabusMapper.selectAll_S", id);
    }

    public void updateSyllabus(Syllabus syllabus){
        getSqlSession().update("com.suzumiya.mapper.SyllabusMapper.updateSyllabus", syllabus);
    }
}