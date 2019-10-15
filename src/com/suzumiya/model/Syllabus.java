package com.suzumiya.model;

import java.util.List;

public class Syllabus {
    private int id;
    private String code;
    private String title;
    private School school;
    private String prepare;
    private String review;
    private int unit;
    private String pre_req;
    private String co_req;
    private String offer_unit;
    private String aim;
    private List<TextBook> textBooks;
    private List<Content> contents;
    private List<Assessment> assessments;
    private List<Cilo> cilos;
    private List<Pilo> pilos;
    private int school_id;
    private int hours;

    public Syllabus() {
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public void setTextBooks(List<TextBook> textBooks) {
        this.textBooks = textBooks;
    }

    public List<TextBook> getTextBooks() {
        return textBooks;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
    }

    public List<Cilo> getCilos() {
        return cilos;
    }

    public void setCilos(List<Cilo> cilos) {
        this.cilos = cilos;
    }

    public List<Pilo> getPilos() {
        return pilos;
    }

    public void setPilos(List<Pilo> pilos) {
        this.pilos = pilos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getPrepare() {
        return prepare;
    }

    public void setPrepare(String prepare) {
        this.prepare = prepare;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getPre_req() {
        return pre_req;
    }

    public void setPre_req(String pre_req) {
        this.pre_req = pre_req;
    }

    public String getCo_req() {
        return co_req;
    }

    public void setCo_req(String co_req) {
        this.co_req = co_req;
    }

    public String getOffer_unit() {
        return offer_unit;
    }

    public void setOffer_unit(String offer_unit) {
        this.offer_unit = offer_unit;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public int getId(){
        return id;
    }

    public void setId (int id){
        this.id = id;
    }
}
