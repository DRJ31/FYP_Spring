package com.suzumiya.dao;

import com.suzumiya.model.AuditSchool;
import com.suzumiya.model.AuditTeacher;
import com.suzumiya.model.Favorite;
import com.suzumiya.model.School;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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

    public List<School> selectFavoriteSchools(@Param("uid") int id) {
        return getSqlSession().selectList("com.suzumiya.mapper.SchoolMapper.selectFavoriteSchools", id);
    }

    public void insertSchool(School school){
        getSqlSession().insert("com.suzumiya.mapper.SchoolMapper.insertSchool", school);
    }

    public void insertAuditSchool(AuditSchool auditSchool) throws Exception {
        getSqlSession().insert("com.suzumiya.mapper.SchoolMapper.insertAuditSchool", auditSchool);
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

    public List<AuditSchool> selectAllAudit() {
        return getSqlSession().selectList("com.suzumiya.mapper.SchoolMapper.selectAllAudit");
    }

    public AuditSchool selectAuditById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.SchoolMapper.selectAuditById", id);
    }

    public void insertAudit(AuditSchool auditSchool) throws Exception {
        getSqlSession().insert("com.suzumiya.mapper.SchoolMapper.insertAudit", auditSchool);
    }

    public void deleteAudit(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SchoolMapper.deleteAudit", id);
    }

    public School getSchoolByName(@Param("name") String name){
        return getSqlSession().selectOne("com.suzumiya.mapper.SchoolMapper.getSchoolByName", name);
    }

    public List<AuditTeacher> selectAllAuditTeacher() {
        return getSqlSession().selectList("com.suzumiya.mapper.SchoolMapper.selectAllAuditTeacher");
    }

    public AuditTeacher selectAuditTeacherById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.SchoolMapper.selectAuditTeacherById", id);
    }

    public void insertAuditTeacher(AuditTeacher auditTeacher) throws Exception {
        getSqlSession().insert("com.suzumiya.mapper.SchoolMapper.insertAuditTeacher", auditTeacher);
    }

    public void deleteAuditTeacher(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.SchoolMapper.deleteAuditTeacher", id);
    }

    public List<AuditTeacher> selectAllAuditSchoolTeacher(@Param("id") int id){
        School school = this.selectById(id);
        return getSqlSession().selectList("com.suzumiya.mapper.SchoolMapper.selectAllAuditSchoolTeacher", school);
    }
}