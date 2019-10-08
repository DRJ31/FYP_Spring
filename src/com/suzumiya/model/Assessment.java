package com.suzumiya.model;

public class Assessment {
    private int id;
    private int syllabus_id;
    private String method;
    private int weighting;
    private String description;
    private Assessment_Cilo assessment_cilo;

    public Assessment() {
    }

    public Assessment_Cilo getAssessment_cilo() {
        return assessment_cilo;
    }

    public void setAssessment_cilo(Assessment_Cilo assessment_cilo) {
        this.assessment_cilo = assessment_cilo;
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
