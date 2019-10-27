package com.suzumiya.service;

import com.suzumiya.dao.AssessmentDao;
import com.suzumiya.model.Assessment;
import com.suzumiya.model.relationship.AssessmentCilo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessmentService {
    private List<Assessment> assessments;
    private AssessmentDao assessmentDao;
    private Assessment assessment;
    private List<AssessmentCilo> assessment_Cilos;
    private AssessmentCilo assessment_cilo;

    public AssessmentService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.assessmentDao = (AssessmentDao) ac.getBean("assessmentDao");
    }

    public void setAssessment_Cilos() {
        this.assessment_Cilos = assessmentDao.selectAC();
    }

    public void setAssessment_cilo(int id) {
        this.assessment_cilo = assessmentDao.selectACById(id);
    }

    public List<AssessmentCilo> getAssessment_Cilos() {
        return assessment_Cilos;
    }

    public AssessmentCilo getAssessment_cilo() {
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
        this.setAssessments();
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

    public Map<String, List<AssessmentCilo>> getACsMap() {
        Map<String, List<AssessmentCilo>> result = new HashMap<>();
        this.setAssessment_Cilos();
        result.put("assessment_Cilos", assessment_Cilos);
        return result;
    }

    public Map<String, AssessmentCilo> getACMap(int id) {
        Map<String, AssessmentCilo> result = new HashMap<>();
        setAssessment_cilo(id);
        result.put("assessment_Cilo", assessment_cilo);
        return result;
    }

    public int insertAC(AssessmentCilo assessment_cilo){
        return assessmentDao.insertAC(assessment_cilo);
    }

    public void deleteAC(int id){
        assessmentDao.deleteAC(id);
    }

    public void updateAssessment(Assessment assessment){
        assessmentDao.updateAssessment(assessment);
    }

    public void updateAC(AssessmentCilo assessmentCilo){
        assessmentDao.updateAC(assessmentCilo);
    }
}
