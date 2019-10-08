package com.suzumiya.model;

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
    private TextBook textBook;
    private Content content;
    private Assessment assessment;
    private Cilo cilo;

    public Syllabus() {
    }

    public Cilo getCilo() {
        return cilo;
    }

    public void setCilo(Cilo cilo) {
        this.cilo = cilo;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public TextBook getTextBook() {
        return textBook;
    }

    public void setTextBook(TextBook textBook) {
        this.textBook = textBook;
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
