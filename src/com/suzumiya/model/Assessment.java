package com.suzumiya.model;

import com.suzumiya.model.relationship.AssessmentCilo;

import java.util.List;

public class Assessment {
    private int id;
    private int syllabus_id;
    private String method;
    private int weighting;
    private String description;
    private List<Integer> cilo_ids;
    private List<AssessmentCilo> assessment_cilos;
    private List<Syllabus> syllabuses;

    public Assessment() {
    }

    public List<Integer> getCilo_ids() {
        return cilo_ids;
    }

    public void setCilo_ids(List<Integer> cilo_ids) {
        this.cilo_ids = cilo_ids;
    }

    public List<Syllabus> getSyllabuses() {
        return syllabuses;
    }

    public void setSyllabuses(List<Syllabus> syllabuses) {
        this.syllabuses = syllabuses;
    }

    public List<AssessmentCilo> getAssessment_cilos() {
        return assessment_cilos;
    }

    public void setAssessment_cilos(List<AssessmentCilo> assessment_cilos) {
        this.assessment_cilos = assessment_cilos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSyllabus_id() {
        return syllabus_id;
    }

    public void setSyllabus_id(int syllabus_id) {
        this.syllabus_id = syllabus_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getWeighting() {
        return weighting;
    }

    public void setWeighting(int weighting) {
        this.weighting = weighting;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
