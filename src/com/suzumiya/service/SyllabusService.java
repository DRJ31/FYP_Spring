package com.suzumiya.service;

import com.suzumiya.dao.SyllabusDao;
import com.suzumiya.model.*;
import com.suzumiya.model.relationship.PiloCilo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyllabusService {
    private List<Syllabus> syllabuses;
    private List<Syllabus> syllabuses_S;
    private SyllabusDao syllabusDao;
    private Syllabus syllabus;
    private Cilo cilo;
    private List<Cilo> cilos;

    public SyllabusService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.syllabusDao = (SyllabusDao) ac.getBean("syllabusDao");
        this.setSyllabuses();
        this.setCilos();
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
        this.syllabus = syllabusDao.selectById(s_id);
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
        result.put("syllabuses", syllabusDao.selectFavoriteSyllabuses(uid));
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
    }

    public void insertFavoriteSyllabus(Favorite favorite){
        syllabusDao.insertFavoriteSyllabus(favorite);
    }

    public void deleteFavoriteSyllabus(Favorite favorite){
        syllabusDao.deleteFavoriteSyllabus(favorite);
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
}
