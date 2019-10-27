package com.suzumiya.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suzumiya.dao.RedisDao;
import com.suzumiya.dao.SyllabusDao;
import com.suzumiya.model.*;
import com.suzumiya.model.relationship.PiloCilo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyllabusService {
    private List<Syllabus> syllabuses;
    private List<Syllabus> syllabuses_S;
    private List<Syllabus> syllabuses_T;
    private SyllabusDao syllabusDao;
    private Syllabus syllabus;
    private Cilo cilo;
    private List<Cilo> cilos;
    private RedisDao redisDao;

    public SyllabusService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.syllabusDao = (SyllabusDao) ac.getBean("syllabusDao");
        this.redisDao = (RedisDao) ac.getBean("redisDao");
        this.setSyllabuses();
        this.setCilos();
    }

    public List<Syllabus> getSyllabuses_T() {
        return syllabuses_T;
    }

    private void setSyllabuses_T(int id) {
        this.syllabuses_T = syllabusDao.selectAll_T(id);
    }

    public List<Syllabus> getSyllabuses_S() {
        return syllabuses_S;
    }

    private void setSyllabuses_S(int id) {
        this.syllabuses_S = syllabusDao.selectAll_S(id);
    }

    private void setCilo(int id) {
        this.cilo = syllabusDao.selectCiloById(id);
    }

    private void setCilos() {
        this.cilos = syllabusDao.selectAllCilo();
    }

    public Cilo getCilo() {
        return cilo;
    }

    public List<Cilo> getCilos() {
        return cilos;
    }

    public List<Syllabus> getSyllabuses() {
        return syllabuses;
    }

    public SyllabusDao getSyllabusDao() {
        return syllabusDao;
    }

    public void setSyllabusDao(SyllabusDao syllabusDao) {
        this.syllabusDao = syllabusDao;
    }

    public Syllabus getSyllabus() {
        return syllabus;
    }

    private void setSyllabuses() {
        this.syllabuses = syllabusDao.selectAll();
    }

    private void setSyllabus(int s_id) {
        String syllabusJson = redisDao.get("syllabus_" + s_id);
        if (StringUtils.isEmpty(syllabusJson)) {
            this.syllabus = syllabusDao.selectById(s_id);
            if (this.syllabus != null) {
                redisDao.set("syllabus_" + s_id, JSONObject.toJSONString(this.syllabus));
            }
        }
        else {
            this.syllabus = JSONObject.parseObject(syllabusJson, Syllabus.class);
        }
    }

    public Map<String, List<Syllabus>> getSyllabusesMap() {
        Map<String, List<Syllabus>> result = new HashMap<>();
        result.put("syllabuses", syllabuses);
        return result;
    }

    public Map<String, Syllabus> getSyllabusMap(int id) {
        Map<String, Syllabus> result = new HashMap<>();
        setSyllabus(id);
        result.put("syllabus", syllabus);
        return result;
    }

    public Map<String, List<Syllabus>> getFavoriteSyllabuses(int uid) {
        Map<String, List<Syllabus>> result = new HashMap<>();
        String favJson = redisDao.get("fav_syllabus_" + uid);
        List<Syllabus> syllabuses;
        if (StringUtils.isEmpty(favJson)) {
            syllabuses = syllabusDao.selectFavoriteSyllabuses(uid);
            if (syllabuses != null) {
                redisDao.set("fav_syllabus_" + uid, JSONObject.toJSONString(syllabuses));
            }
        }
        else {
            syllabuses = JSONObject.parseArray(favJson, Syllabus.class);
        }
        result.put("syllabuses", syllabuses);
        return result;
    }

    public Syllabus getInsertedSyllabus(Syllabus syllabus) {
        return syllabusDao.selectByInfo(syllabus);
    }

    public void insertSyllabus(Syllabus syllabus){
        syllabusDao.insertSyllabus(syllabus);
    }

    public void deleteSyllabus(int sid){
        syllabusDao.deleteSyllabus(sid);
        String userJson = redisDao.get("syllabus_" + sid);
        if (!StringUtils.isEmpty(userJson)) {
            redisDao.del("syllabus_" + sid);
        }
    }

    public void insertFavoriteSyllabus(Favorite favorite){
        syllabusDao.insertFavoriteSyllabus(favorite);
        String favJson = redisDao.get("fav_syllabus_" + favorite.getUser_id());
        List<Syllabus> favorites = JSONObject.parseArray(favJson, Syllabus.class);
        favorites.add(syllabusDao.selectById(favorite.getSyllabus_id()));
        redisDao.set("fav_syllabus_" + favorite.getUser_id(), JSONObject.toJSONString(favorites));
    }

    public void deleteFavoriteSyllabus(Favorite favorite){
        syllabusDao.deleteFavoriteSyllabus(favorite);
        String favJson = redisDao.get("fav_syllabus_" + favorite.getUser_id());
        List<Syllabus> favorites = JSONObject.parseArray(favJson, Syllabus.class);
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getId() == favorite.getSyllabus_id())
                favorites.remove(favorites.get(i));
        }
        redisDao.set("fav_syllabus_" + favorite.getUser_id(), JSONObject.toJSONString(favorites));
    }

    public Favorite checkFavoriteDuplicate(Favorite favorite){
        return syllabusDao.checkFavoriteDuplicate(favorite);
    }

    public Map<String, List<Cilo>> selectAllCilo(){
        Map<String, List<Cilo>> result = new HashMap<>();
        result.put("cilos", cilos);
        return result;
    }

    public Map<String, Cilo> selectCiloById(int id){
        Map<String, Cilo> result = new HashMap<>();
        setCilo(id);
        result.put("cilo", cilo);
        return result;
    }

    public int insertCilo(Cilo cilo){
        return syllabusDao.insertCilo(cilo);
    }

    public void deleteCilo(int id){
        syllabusDao.deleteCilo(id);
    }

    public int insertPilo(Pilo pilo){
        return syllabusDao.insertPilo(pilo);
    }

    public void deletePilo(int id){
        syllabusDao.deletePilo(id);
    }

    public int insertPC(PiloCilo pilo_cilo){
        return syllabusDao.insertPC(pilo_cilo);
    }

    public void deletePC(int id){
        syllabusDao.deletePC(id);
    }

    public int insertTla(Tla tla){
        return syllabusDao.insertTla(tla);
    }

    public void deleteTla(int id){
        syllabusDao.deleteTla(id);
    }

    public int insertContent(Content content){
         return syllabusDao.insertContent(content);
    }

    public void deleteContent(int id){
        syllabusDao.deleteContent(id);
    }

    public Map<String, List<Syllabus>> selectSyllabuses_S(int id) {
        Map<String, List<Syllabus>> result = new HashMap<>();
        setSyllabuses_S(id);
        result.put("syllabuses", syllabuses_S);
        return result;
    }

    public void updateSyllabus(Syllabus syllabus){
        syllabusDao.updateSyllabus(syllabus);
        String userJson = redisDao.get("syllabus_" + syllabus.getId());
        if (!StringUtils.isEmpty(userJson)) {
            redisDao.del("syllabus_" + syllabus.getId());
        }
    }

    public void updateCilo(Cilo cilo){
        syllabusDao.updateCilo(cilo);
    }

    public void updatePilo(Pilo pilo){
        syllabusDao.updatePilo(pilo);
    }

    public void updatePC(PiloCilo piloCilo){
        syllabusDao.updatePC(piloCilo);
    }

    public void updateTla(Tla tla){
        syllabusDao.updateTla(tla);
    }

    public void updateContent(Content content){
        syllabusDao.updateContent(content);
    }

    public Map<String, List<Syllabus>> selectSyllabuses_T(int id) {
        Map<String, List<Syllabus>> result = new HashMap<>();
        setSyllabuses_T(id);
        result.put("syllabuses", syllabuses_T);
        return result;
    }
}
