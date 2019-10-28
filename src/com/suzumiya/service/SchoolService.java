package com.suzumiya.service;

import com.alibaba.fastjson.JSONObject;
import com.suzumiya.dao.RedisDao;
import com.suzumiya.dao.SchoolDao;
import com.suzumiya.model.audit.AuditSchool;
import com.suzumiya.model.audit.AuditTeacher;
import com.suzumiya.model.Favorite;
import com.suzumiya.model.user.School;
import org.springframework.util.StringUtils;

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
    private RedisDao redisDao;

    public void setRedisDao(RedisDao redisDao) {
        this.redisDao = redisDao;
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
        String schoolJson = redisDao.get("school_" + sid);
        if (StringUtils.isEmpty(schoolJson)) {
            this.school = schoolDao.selectById(sid);
            if (this.school != null) {
                redisDao.set("school_" + sid, JSONObject.toJSONString(this.school));
            }
        }
        else {
            this.school = JSONObject.parseObject(schoolJson, School.class);
        }
    }

    public Map<String, List<School>> getSchoolsMap() {
        Map<String, List<School>> result = new HashMap<>();
        this.setSchools();
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
        String favJson = redisDao.get("fav_school_" + id);
        List<School> syllabuses;
        if (StringUtils.isEmpty(favJson)) {
            syllabuses = schoolDao.selectFavoriteSchools(id);
            if (syllabuses != null) {
                redisDao.set("fav_school_" + id, JSONObject.toJSONString(syllabuses));
            }
        }
        else {
            syllabuses = JSONObject.parseArray(favJson, School.class);
        }
        result.put("schools", syllabuses);
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
        String userJson = redisDao.get("school_" + id);
        if (!StringUtils.isEmpty(userJson)) {
            redisDao.del("school_" + id);
        }
    }

    public void insertFavoriteSchool(Favorite favorite){
        schoolDao.insertFavoriteSchool(favorite);
        String favJson = redisDao.get("fav_school_" + favorite.getUser_id());
        List<School> favorites = JSONObject.parseArray(favJson, School.class);
        favorites.add(schoolDao.selectById(favorite.getSchool_id()));
        redisDao.set("fav_school_" + favorite.getUser_id(), JSONObject.toJSONString(favorites));
    }

    public void deleteFavoriteSchool(Favorite favorite){
        schoolDao.deleteFavoriteSchool(favorite);
        String favJson = redisDao.get("fav_school_" + favorite.getUser_id());
        List<School> favorites = JSONObject.parseArray(favJson, School.class);
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getId() == favorite.getSchool_id())
                favorites.remove(favorites.get(i));
        }
        redisDao.set("fav_school_" + favorite.getUser_id(), JSONObject.toJSONString(favorites));
    }

    public Favorite checkFavoriteDuplicate(Favorite favorite){
        return schoolDao.checkFavoriteDuplicate(favorite);
    }

    public Map<String, List<AuditSchool>> getAuditSchoolsMap() {
        Map<String, List<AuditSchool>> result = new HashMap<>();
        this.setAuditSchools();
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
        this.setAuditTeachers();
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

    public void updateSchool(School school){
        schoolDao.updateSchool(school);
        String schoolJson = redisDao.get("school_" + school.getId());
        if (!StringUtils.isEmpty(schoolJson)) {
            String jsonString = JSONObject.toJSONString(school);
            redisDao.set("school_" + school.getId(), jsonString);
        }
    }
}
