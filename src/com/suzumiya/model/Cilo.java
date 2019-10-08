package com.suzumiya.model;

public class Cilo {
    private int id;
    private int syllabus_id;
    private int cilo_id;
    private String content;

    public Cilo() {
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

    public int getCilo_id() {
        return cilo_id;
    }

    public void setCilo_id(int cilo_id) {
        this.cilo_id = cilo_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
