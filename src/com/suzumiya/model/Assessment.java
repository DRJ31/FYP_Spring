package com.suzumiya.model;

import java.util.List;

public class Assessment {
    private int id;
    private int syllabus_id;
    private String method;
    private int weighting;
    private String description;
    private List<Assessment_Cilo> assessment_cilos;

    public Assessment() {
    }

    public List<Assessment_Cilo> getAssessment_cilos() {
        return assessment_cilos;
    }

    public void setAssessment_cilos(List<Assessment_Cilo> assessment_cilos) {
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
