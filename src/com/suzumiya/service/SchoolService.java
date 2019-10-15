package com.suzumiya.service;

import com.suzumiya.dao.SchoolDao;
import com.suzumiya.model.Favorite;
import com.suzumiya.model.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchoolService {
    private List<School> schools;
    private SchoolDao schoolDao;
    private School school;

    public SchoolService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.schoolDao = (SchoolDao) ac.getBean("schoolDao");
        this.setSchools();
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

    public Map<String, School> getSchoolMap(int uid) {
        Map<String, School> result = new HashMap<>();
        setSchool(uid);
        result.put("school", school);
        return result;
    }

    public Map<String, List<School>> getFavoriteSchools(int uid) {
        Map<String, List<School>> result = new HashMap<>();
        result.put("schools", schoolDao.selectFavoriteSchools(uid));
        return result;
    }

    public void insertSchool(School school){
        schoolDao.insertSchool(school);
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
}
