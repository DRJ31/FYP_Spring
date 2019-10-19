package com.suzumiya.service;

import com.suzumiya.dao.AssessmentDao;
import com.suzumiya.model.Assessment;
import com.suzumiya.model.Assessment_Cilo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessmentService {
    private List<Assessment> assessments;
    private AssessmentDao assessmentDao;
    private Assessment assessment;
    private List<Assessment_Cilo> assessment_Cilos;
    private Assessment_Cilo assessment_cilo;

    public AssessmentService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.assessmentDao = (AssessmentDao) ac.getBean("assessmentDao");
        this.setAssessments();
        this.setAssessment_Cilos();
    }

    public void setAssessment_Cilos() {
        this.assessment_Cilos = assessmentDao.selectAC();
    }

    public void setAssessment_cilo(int id) {
        this.assessment_cilo = assessmentDao.selectACById(id);
    }

    public List<Assessment_Cilo> getAssessment_Cilos() {
        return assessment_Cilos;
    }

    public Assessment_Cilo getAssessment_cilo() {
        return assessment_cilo;
    }

    public void setAssessments() {
        this.assessments = assessmentDao.selectAll();
    }

    public void setAssessmentDao(AssessmentDao assessmentDao) {
        this.assessmentDao = assessmentDao;
    }

    public void setAssessment(int id) {
        this.assessment = assessmentDao.selectById(id);
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public AssessmentDao getAssessmentDao() {
        return assessmentDao;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public Map<String, List<Assessment>> getAssessmentsMap() {
        Map<String, List<Assessment>> result = new HashMap<>();
        result.put("assessments", assessments);
        return result;
    }

    public Map<String, Assessment> getAssessmentMap(int id) {
        Map<String, Assessment> result = new HashMap<>();
        setAssessment(id);
        result.put("assessment", assessment);
        return result;
    }

    public int insertAssessment(Assessment assessment){
        return assessmentDao.insertAssessment(assessment);
    }

    public void deleteAssessment(int id){
        assessmentDao.deleteAssessment(id);
    }

    public Map<String, List<Assessment_Cilo>> getACsMap() {
        Map<String, List<Assessment_Cilo>> result = new HashMap<>();
        result.put("assessment_Cilos", assessment_Cilos);
        return result;
    }

    public Map<String, Assessment_Cilo> getACMap(int id) {
        Map<String, Assessment_Cilo> result = new HashMap<>();
        setAssessment_cilo(id);
        result.put("assessment_Cilo", assessment_cilo);
        return result;
    }

    public int insertAC(Assessment_Cilo assessment_cilo){
        return assessmentDao.insertAC(assessment_cilo);
    }

    public void deleteAC(int id){
        assessmentDao.deleteAC(id);
    }
}
