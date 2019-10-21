package com.suzumiya.service;

import com.suzumiya.dao.SchoolDao;
import com.suzumiya.model.audit.AuditSchool;
import com.suzumiya.model.audit.AuditTeacher;
import com.suzumiya.model.Favorite;
import com.suzumiya.model.user.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolService {
    private List<School> schools;
    private SchoolDao schoolDao;
    private School school;
    private AuditSchool auditSchool;
    private AuditTeacher auditTeacher;
    private List<AuditSchool> auditSchools;
    private List<AuditTeacher> auditTeachers;
    private List<AuditTeacher> auditTeacher_S;

    public SchoolService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.schoolDao = (SchoolDao) ac.getBean("schoolDao");
        this.setSchools();
        this.setAuditSchools();
        this.setAuditTeachers();
    }

    public void setAuditTeacher_S(int id){
        this.auditTeacher_S = schoolDao.selectAllAuditSchoolTeacher(id);
    }

    public AuditTeacher getAuditTeacher() {
        return auditTeacher;
    }

    public void setAuditTeacher(int id) {
        this.auditTeacher = schoolDao.selectAuditTeacherById(id);
    }

    public List<AuditTeacher> getAuditTeachers() {
        return auditTeachers;
    }

    public void setAuditTeachers() {
        this.auditTeachers = schoolDao.selectAllAuditTeacher();
    }

    public AuditSchool getAuditSchool() {
        return auditSchool;
    }

    public void setAuditSchool(int id) {
        this.auditSchool = schoolDao.selectAuditById(id);
    }

    public List<AuditSchool> getAuditSchools() {
        return auditSchools;
    }

    public void setAuditSchools() {
        this.auditSchools = schoolDao.selectAllAudit();
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools() {
        this.schools = schoolDao.selectAll();
    }

    public SchoolDao getSchoolDao() {
        return schoolDao;
    }

    public void setSchoolDao(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(int sid) {
        this.school = schoolDao.selectById(sid);
    }

    public Map<String, List<School>> getSchoolsMap() {
        Map<String, List<School>> result = new HashMap<>();
        result.put("schools", schools);
        return result;
    }

    public Map<String, School> getSchoolMap(int id) {
        Map<String, School> result = new HashMap<>();
        setSchool(id);
        result.put("school", school);
        return result;
    }

    public Map<String, List<School>> getFavoriteSchools(int id) {
        Map<String, List<School>> result = new HashMap<>();
        result.put("schools", schoolDao.selectFavoriteSchools(id));
        return result;
    }

    public void insertSchool(School school){
        schoolDao.insertSchool(school);
    }

    public void insertAuditSchool(AuditSchool auditSchool) throws Exception {
        schoolDao.insertAuditSchool(auditSchool);
    }

    public void deleteSchool(int id){
        schoolDao.deleteSchool(id);
    }

    public void insertFavoriteSchool(Favorite favorite){
        schoolDao.insertFavoriteSchool(favorite);
    }

    public void deleteFavoriteSchool(Favorite favorite){
        schoolDao.deleteFavoriteSchool(favorite);
    }

    public Favorite checkFavoriteDuplicate(Favorite favorite){
        return schoolDao.checkFavoriteDuplicate(favorite);
    }

    public Map<String, List<AuditSchool>> getAuditSchoolsMap() {
        Map<String, List<AuditSchool>> result = new HashMap<>();
        result.put("audits", auditSchools);
        return result;
    }

    public Map<String, AuditSchool> getAuditSchoolMap(int id) {
        Map<String, AuditSchool> result = new HashMap<>();
        setAuditSchool(id);
        result.put("audit", auditSchool);
        return result;
    }

    public void insertAudit(AuditSchool auditSchool) throws Exception {
        schoolDao.insertAudit(auditSchool);
    }

    public void deleteAudit(int id){
        schoolDao.deleteAudit(id);
    }

    public School getSchoolByName(String name){
        return schoolDao.getSchoolByName(name);
    }

    public Map<String, List<AuditTeacher>> getAuditTeachersMap() {
        Map<String, List<AuditTeacher>> result = new HashMap<>();
        result.put("audits", auditTeachers);
        return result;
    }

    public Map<String, AuditTeacher> getAuditTeacherMap(int id) {
        Map<String, AuditTeacher> result = new HashMap<>();
        setAuditTeacher(id);
        result.put("audit", auditTeacher);
        return result;
    }

    public void insertAuditTeacher(AuditTeacher auditTeacher) throws Exception {
        schoolDao.insertAuditTeacher(auditTeacher);
    }

    public void deleteAuditTeacher(int id){
        schoolDao.deleteAuditTeacher(id);
    }

    public Map<String, List<AuditTeacher>> getAuditSchoolTeachersMap(int id) {
        Map<String, List<AuditTeacher>> result = new HashMap<>();
        setAuditTeacher_S(id);
        result.put("audits", auditTeacher_S);
        return result;
    }
}
